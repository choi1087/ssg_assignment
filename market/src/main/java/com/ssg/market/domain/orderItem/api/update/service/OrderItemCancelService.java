package com.ssg.market.domain.orderItem.api.update.service;

import com.ssg.market.data.model.entity.Item;
import com.ssg.market.data.model.entity.Order;
import com.ssg.market.data.model.entity.OrderItem;
import com.ssg.market.data.repository.ItemRepository;
import com.ssg.market.data.repository.OrderItemRepository;
import com.ssg.market.data.repository.OrderRepository;
import com.ssg.market.domain.orderItem.api.update.dto.req.OrderItemCancelReqDTO;
import com.ssg.market.domain.orderItem.api.update.dto.res.OrderItemCancelResDTO;
import com.ssg.market.global.errorhandling.BusinessException;
import com.ssg.market.global.errorhandling.ErrorCode;
import com.ssg.market.global.errorhandling.exception.EntityIsNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemCancelService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderItemCancelResDTO cancelOrder(OrderItemCancelReqDTO reqDTO) {
        Item item = itemRepository.findById(reqDTO.getItemId()).orElseThrow(() -> new EntityIsNullException(ErrorCode.ITEM_NOT_FOUND, List.of(reqDTO.getItemId())));
        Order order = orderRepository.findById(reqDTO.getOrderId()).orElseThrow(() -> new EntityIsNullException(ErrorCode.ORDER_NOT_FOUND, List.of(reqDTO.getOrderId())));

        // 주문 목록 조회
        OrderItem orderItem = orderItemRepository.findByOrderAndItem(order, item)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_ITEM_NOT_FOUND));

        // 주문 목록 취소
        orderItem.cancel();

        return OrderItemCancelResDTO.builder()
                .itemId(reqDTO.getItemId())
                .itemName(item.getName())
                .quantity(orderItem.getQuantity())
                .refundPrice(orderItem.getFinalPrice())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
