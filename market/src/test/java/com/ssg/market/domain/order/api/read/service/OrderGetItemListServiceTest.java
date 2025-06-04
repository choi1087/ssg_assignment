package com.ssg.market.domain.order.api.read.service;

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
import com.ssg.market.domain.order.api.read.dto.res.OrderGetItemListResDTO;
import com.ssg.market.global.errorhandling.ErrorCode;
import com.ssg.market.global.errorhandling.exception.EntityIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class OrderGetItemListServiceTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderCreateService orderCreateService;
    @Autowired
    private OrderGetItemListService orderGetItemListService;

    private final Long VALID_ITEM_ID_1 = 1000000001L;
    private final Long VALID_ITEM_ID_2 = 1000000002L;
    private Long VALID_ORDER_ID;

    @BeforeEach
    void setUp() {
        Item item1 = itemRepository.findById(VALID_ITEM_ID_1).orElseThrow();
        int discountQuantity1 = item1.getStock() / 2;
        OrderCreateReqDTO reqDTO1 = OrderCreateReqDTO.builder()
                .itemId(VALID_ITEM_ID_1)
                .quantity(discountQuantity1)
                .build();
        Item item2 = itemRepository.findById(VALID_ITEM_ID_2).orElseThrow();
        int discountQuantity2 = item2.getStock() / 2;
        OrderCreateReqDTO reqDTO2 = OrderCreateReqDTO.builder()
                .itemId(VALID_ITEM_ID_2)
                .quantity(discountQuantity2)
                .build();
        OrderCreateResDTO resDTO = orderCreateService.createOrder(List.of(reqDTO1, reqDTO2));
        VALID_ORDER_ID = resDTO.getOrderId();
    }

    @Test
    @DisplayName("주문 상품 조회")
    void getOrderItem() {
        // given
        Order order = orderRepository.findById(VALID_ORDER_ID).orElseThrow();

        // when
        OrderGetItemListResDTO orderItemList = orderGetItemListService.getOrderItemList(VALID_ORDER_ID);
        List<OrderGetItemListResDTO.ItemInfoDTO> itemInfoDTOList = orderItemList.getItemInfoDTOList();
        Integer totalPrice = orderItemList.getTotalPrice();

        // then
        assertThat(itemInfoDTOList).hasSize(2);
        assertThat(totalPrice).isEqualTo(order.getTotalPrice());
    }

    @Test
    @DisplayName("존재하지 않는 주문번호")
    void invalidOrder() {
        // given
        Long invalidOrderId = VALID_ORDER_ID + 10;

        // when & then
        assertThatThrownBy(() -> orderGetItemListService.getOrderItemList(invalidOrderId))
                .isInstanceOf(EntityIsNullException.class)
                .satisfies(ex -> {
                    EntityIsNullException ee = (EntityIsNullException) ex;
                    assertThat(ee.getErrorCode()).isEqualTo(ErrorCode.ORDER_NOT_FOUND);
                });
    }
}