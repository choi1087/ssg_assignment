package com.ssg.market.domain.order.api.read.service;

import com.ssg.market.data.model.entity.Item;
import com.ssg.market.data.model.entity.Order;
import com.ssg.market.data.model.entity.OrderItem;
import com.ssg.market.data.repository.OrderItemRepository;
import com.ssg.market.data.repository.OrderRepository;
import com.ssg.market.domain.order.api.read.dto.res.OrderGetItemListResDTO;
import com.ssg.market.domain.order.api.read.dto.res.OrderGetItemListResDTO.ItemInfoDTO;
import com.ssg.market.global.errorhandling.ErrorCode;
import com.ssg.market.global.errorhandling.exception.EntityIsNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderGetItemListService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderGetItemListResDTO getOrderItemList(Long orderId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityIsNullException(ErrorCode.ORDER_NOT_FOUND, List.of(orderId)));

        // 주문 목록 조회 및 DTO List 생성
        List<OrderItem> orderItemList = orderItemRepository.findByOrderAndStatus(orderId);
        List<ItemInfoDTO> itemInfoDTOList = createItemInfoDTOList(orderItemList);

        Integer totalPrice = order.getTotalPrice();
        return OrderGetItemListResDTO.builder()
                .itemInfoDTOList(itemInfoDTOList)
                .totalPrice(totalPrice)
                .build();
    }

    private static List<ItemInfoDTO> createItemInfoDTOList(List<OrderItem> orderItemList) {
        List<ItemInfoDTO> itemInfoDTOList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            Item item = orderItem.getItem();
            ItemInfoDTO itemInfoDTO = ItemInfoDTO.builder()
                    .itemId(item.getId())
                    .itemName(item.getName())
                    .quantity(orderItem.getQuantity())
                    .finalPrice(orderItem.getFinalPrice())
                    .build();
            itemInfoDTOList.add(itemInfoDTO);
        }
        return itemInfoDTOList;
    }
}
