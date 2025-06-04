package com.ssg.market.domain.order.api.create.service;

import com.ssg.market.data.model.entity.Item;
import com.ssg.market.data.model.entity.Order;
import com.ssg.market.data.model.entity.OrderItem;
import com.ssg.market.data.repository.ItemRepository;
import com.ssg.market.data.repository.OrderItemRepository;
import com.ssg.market.data.repository.OrderRepository;
import com.ssg.market.domain.order.api.create.dto.req.OrderCreateReqDTO;
import com.ssg.market.domain.order.api.create.dto.res.OrderCreateResDTO;
import com.ssg.market.global.errorhandling.ErrorCode;
import com.ssg.market.global.errorhandling.exception.ItemDuplicatedException;
import com.ssg.market.global.errorhandling.exception.EntityIsNullException;
import com.ssg.market.global.errorhandling.exception.ItemStockNotEnoughException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ssg.market.domain.order.api.create.dto.res.OrderCreateResDTO.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCreateService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderCreateResDTO createOrder(List<OrderCreateReqDTO> reqDTOList) {
        // 상품 요청 ID별 엔티티 조회
        List<Long> itemIdList = reqDTOList.stream().map(OrderCreateReqDTO::getItemId).toList();
        List<Item> itemList = itemRepository.findByIdInForUpdateStock(itemIdList);
        Map<Long, Item> itemMap = itemList.stream()
                .collect(Collectors.toMap(Item::getId, Function.identity()));

        // 유효성 검사
        checkValidation(reqDTOList, itemMap);

        // 주문 및 주문 목록 생성
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();
        List<ItemInfoDTO> itemInfoDTOList = new ArrayList<>();

        for (OrderCreateReqDTO reqDTO : reqDTOList) {
            createOrderItemAndItemInfo(itemMap, order, orderItemList, itemInfoDTOList, reqDTO);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItemList);

        return OrderCreateResDTO.builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .itemInfoList(itemInfoDTOList)
                .build();
    }

    private static void createOrderItemAndItemInfo(Map<Long, Item> itemMap, Order order, List<OrderItem> orderItemList, List<ItemInfoDTO> itemInfoDTOList, OrderCreateReqDTO reqDTO) {
        Item item = itemMap.get(reqDTO.getItemId());

        // 주문 목록 생성
        OrderItem orderItem = OrderItem.create(order, item, reqDTO.getQuantity());
        orderItemList.add(orderItem);

        // 상품 정보 삽입
        itemInfoDTOList.add(ItemInfoDTO.builder()
                .itemId(item.getId())
                .finalPrice(orderItem.getFinalPrice())
                .build());
        order.addOrderItem(orderItem);
    }

    private static void checkValidation(List<OrderCreateReqDTO> reqDTOList, Map<Long, Item> itemMap) {
        // 상품 ID 중복 여부 확인
        checkDuplicatedItemId(reqDTOList);

        List<Long> notFoundIdList = new ArrayList<>();
        List<Long> notEnoughStockList = new ArrayList<>();

        for (OrderCreateReqDTO reqDTO : reqDTOList) {
            Item item = itemMap.get(reqDTO.getItemId());

            // 존재하지 않은 상품 ID 목록
            if (item == null) {
                notFoundIdList.add(reqDTO.getItemId());
                continue;
            }

            // 재고 부족 목록
            if (item.getStock() < reqDTO.getQuantity()) {
                notEnoughStockList.add(reqDTO.getItemId());
            }
        }

        if (!notFoundIdList.isEmpty()) throw new EntityIsNullException(ErrorCode.ITEM_NOT_FOUND, notFoundIdList);
        if (!notEnoughStockList.isEmpty()) throw new ItemStockNotEnoughException(notEnoughStockList);
    }

    // 상품 ID 중복 요청 여부 확인
    // 하나의 주문에 동일한 상품 ID가 복수개 있는 것은 요청 의도와 맞지 않다고 생각하기 때문에 에러로 판단
    private static void checkDuplicatedItemId(List<OrderCreateReqDTO> reqDTOList) {
        Set<Long> idSet = new HashSet<>();
        List<Long> duplicatedIdList = new ArrayList<>();
        for (OrderCreateReqDTO reqDTO : reqDTOList) {
            if (idSet.contains(reqDTO.getItemId())) {
                duplicatedIdList.add(reqDTO.getItemId());
            } else {
                idSet.add(reqDTO.getItemId());
            }
        }

        if (!duplicatedIdList.isEmpty()) throw new ItemDuplicatedException(duplicatedIdList);
    }
}
