package com.ssg.market.domain.orderItem.api.update.service;

import com.ssg.market.data.model.entity.Item;
import com.ssg.market.data.model.entity.Order;
import com.ssg.market.data.model.entity.OrderItem;
import com.ssg.market.data.model.enumerate.OrderItemStatus;
import com.ssg.market.data.repository.ItemRepository;
import com.ssg.market.data.repository.OrderItemRepository;
import com.ssg.market.data.repository.OrderRepository;
import com.ssg.market.domain.order.api.create.dto.req.OrderCreateReqDTO;
import com.ssg.market.domain.order.api.create.dto.res.OrderCreateResDTO;
import com.ssg.market.domain.order.api.create.service.OrderCreateService;
import com.ssg.market.domain.orderItem.api.update.dto.req.OrderItemCancelReqDTO;
import com.ssg.market.domain.orderItem.api.update.dto.res.OrderItemCancelResDTO;
import com.ssg.market.global.errorhandling.BusinessException;
import com.ssg.market.global.errorhandling.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class OrderItemCancelServiceTest {

    @Autowired
    private OrderItemCancelService orderItemCancelService;
    @Autowired
    private OrderCreateService orderCreateService;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;

    private Long VALID_ORDER_ID;
    private Long INVALID_ORDER_ID = -1L;
    private final Long VALID_ITEM_ID = 1000000001L;
    private final Long INVALID_ITEM_ID = 999999999L;
    private int VALID_ITEM_QUANTITY;

    @BeforeEach
    void setUp() {
        Item item = itemRepository.findById(VALID_ITEM_ID).orElseThrow();
        VALID_ITEM_QUANTITY = item.getStock() / 2;
        OrderCreateReqDTO reqDTO = OrderCreateReqDTO.builder()
                .itemId(VALID_ITEM_ID)
                .quantity(VALID_ITEM_QUANTITY)
                .build();
        OrderCreateResDTO resDTO = orderCreateService.createOrder(List.of(reqDTO));
        VALID_ORDER_ID = resDTO.getOrderId();
    }

    @Test
    @DisplayName("주문 상품 개별 취소 성공")
    void cancelOrderItem() {
        // given
        Item item = itemRepository.findById(VALID_ITEM_ID).orElseThrow();
        Order order = orderRepository.findById(VALID_ORDER_ID).orElseThrow();
        OrderItem orderItem = orderItemRepository.findByOrderAndItem(order, item).orElseThrow();
        OrderItemCancelReqDTO reqDTO = OrderItemCancelReqDTO.builder()
                .orderId(VALID_ORDER_ID)
                .itemId(VALID_ITEM_ID)
                .build();

        Integer originTotalPrice = order.getTotalPrice();
        Integer originStock = item.getStock();

        // when
        OrderItemCancelResDTO resDTO = orderItemCancelService.cancelOrder(reqDTO);

        // then
        assertThat(resDTO.getItemId()).isEqualTo(item.getId());
        assertThat(resDTO.getItemName()).isEqualTo(item.getName());
        assertThat(resDTO.getQuantity()).isEqualTo(orderItem.getQuantity());
        assertThat(resDTO.getRefundPrice()).isEqualTo(orderItem.getFinalPrice());

        assertThat(item.getStock()).isEqualTo(originStock + VALID_ITEM_QUANTITY);
        assertThat(order.getTotalPrice()).isEqualTo(originTotalPrice + orderItem.getFinalPrice());
        assertThat(orderItem.getStatus()).isEqualTo(OrderItemStatus.CANCELLED);
    }

    @Test
    @DisplayName("이미 취소한 주문 상품 정보")
    void alreadyCanceled() {
        // given
        OrderItemCancelReqDTO reqDTO = OrderItemCancelReqDTO.builder()
                .orderId(VALID_ORDER_ID)
                .itemId(VALID_ITEM_ID)
                .build();

        // when
        orderItemCancelService.cancelOrder(reqDTO);

        // then
        assertThatThrownBy(() -> orderItemCancelService.cancelOrder(reqDTO))
                .isInstanceOf(BusinessException.class)
                .satisfies(ex -> {
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getErrorCode()).isEqualTo(ErrorCode.ORDER_ITEM_ALREADY_DELETED);
                });
    }

    @Test
    @DisplayName("존재하지 않는 주문번호")
    void invalidOrderId() {
        // given
        OrderItemCancelReqDTO reqDTO = OrderItemCancelReqDTO.builder()
                .orderId(INVALID_ORDER_ID)
                .itemId(VALID_ITEM_ID)
                .build();

        // when & then
        assertThatThrownBy(() -> orderItemCancelService.cancelOrder(reqDTO))
                .isInstanceOf(BusinessException.class)
                .satisfies(ex -> {
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getErrorCode()).isEqualTo(ErrorCode.ORDER_NOT_FOUND);
                });
    }

    @Test
    @DisplayName("존재하지 않는 상품 아이디")
    void invalidItemId() {
        // given
        OrderItemCancelReqDTO reqDTO = OrderItemCancelReqDTO.builder()
                .orderId(VALID_ORDER_ID)
                .itemId(INVALID_ITEM_ID)
                .build();

        // when & then
        assertThatThrownBy(() -> orderItemCancelService.cancelOrder(reqDTO))
                .isInstanceOf(BusinessException.class)
                .satisfies(ex -> {
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getErrorCode()).isEqualTo(ErrorCode.ITEM_NOT_FOUND);
                });
    }
}