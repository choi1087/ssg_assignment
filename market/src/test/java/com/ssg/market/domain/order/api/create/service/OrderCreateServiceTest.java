package com.ssg.market.domain.order.api.create.service;

import com.ssg.market.data.model.entity.Item;
import com.ssg.market.data.repository.ItemRepository;
import com.ssg.market.domain.order.api.create.dto.req.OrderCreateReqDTO;
import com.ssg.market.domain.order.api.create.dto.res.OrderCreateResDTO;
import com.ssg.market.global.errorhandling.exception.ItemDuplicatedException;
import com.ssg.market.global.errorhandling.exception.EntityIsNullException;
import com.ssg.market.global.errorhandling.exception.ItemStockNotEnoughException;
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
class OrderCreateServiceTest {

    @Autowired
    private OrderCreateService orderCreateService;
    @Autowired
    private ItemRepository itemRepository;

    private final Long VALID_ITEM_ID = 1000000001L;
    private final Long INVALID_ITEM_ID = 999999999L;
    private final int VALID_QUANTITY = 1;


    @Test
    @DisplayName("주문 생성 성공")
    void createOrder() {
        // given
        Item item = itemRepository.findById(VALID_ITEM_ID).orElseThrow();
        int originalStock = item.getStock();
        OrderCreateReqDTO reqDTO = OrderCreateReqDTO.builder()
                .itemId(item.getId())
                .quantity(VALID_QUANTITY)
                .build();
        List<OrderCreateReqDTO> reqDTOList = List.of(reqDTO);

        // when
        OrderCreateResDTO resDTO = orderCreateService.createOrder(reqDTOList);

        // then
        assertThat(resDTO.getOrderId()).isNotNull();
        assertThat(resDTO.getItemInfoList()).hasSize(1);
        assertThat(resDTO.getTotalPrice()).isEqualTo(item.calculateFinalPrice(VALID_QUANTITY));

        // 영속성 컨텍스트에서 item과 동일한 객체를 가져옴
        Item updatedItem = itemRepository.findById(item.getId()).orElseThrow();
        assertThat(updatedItem.getStock()).isEqualTo(originalStock - VALID_QUANTITY);
    }

    @Test
    @DisplayName("중복 ID 요청")
    void checkDuplicatedId() {
        // given
        OrderCreateReqDTO reqDTO1 = OrderCreateReqDTO.builder()
                .itemId(VALID_ITEM_ID)
                .quantity(VALID_QUANTITY)
                .build();
        OrderCreateReqDTO reqDTO2 = OrderCreateReqDTO.builder()
                .itemId(VALID_ITEM_ID)
                .quantity(VALID_QUANTITY)
                .build();
        List<OrderCreateReqDTO> reqDTOList = List.of(reqDTO1, reqDTO2);

        // when & then
        assertThatThrownBy(() -> orderCreateService.createOrder(reqDTOList))
                .isInstanceOf(ItemDuplicatedException.class);
    }

    @Test
    @DisplayName("존재하지 않는 ID")
    void notFoundItemId() {
        // given
        OrderCreateReqDTO reqDTO = OrderCreateReqDTO.builder()
                .itemId(INVALID_ITEM_ID)
                .quantity(VALID_QUANTITY)
                .build();
        List<OrderCreateReqDTO> reqDTOList = List.of(reqDTO);

        // when & then
        assertThatThrownBy(() -> orderCreateService.createOrder(reqDTOList))
                .isInstanceOf(EntityIsNullException.class);
    }

    @Test
    @DisplayName("재고 부족")
    void notEnoughStock() {
        // given
        Item item = itemRepository.findById(VALID_ITEM_ID).orElseThrow();
        int invalidQuantity = item.getStock() + 1;
        OrderCreateReqDTO reqDTO = OrderCreateReqDTO.builder()
                .itemId(item.getId())
                .quantity(invalidQuantity)
                .build();
        List<OrderCreateReqDTO> reqDTOList = List.of(reqDTO);

        // when & then
        assertThatThrownBy(() -> orderCreateService.createOrder(reqDTOList))
                .isInstanceOf(ItemStockNotEnoughException.class);
    }
}