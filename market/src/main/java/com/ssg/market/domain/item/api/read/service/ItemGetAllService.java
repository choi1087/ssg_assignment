package com.ssg.market.domain.item.api.read.service;

import com.ssg.market.data.model.entity.Item;
import com.ssg.market.data.repository.ItemRepository;
import com.ssg.market.domain.item.api.read.dto.ItemGetAllResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemGetAllService {
    private final ItemRepository itemRepository;

    public List<ItemGetAllResDTO> getItemlist() {
        List<Item> itemList = itemRepository.findAll();
        return getItemGetAllResDTOList(itemList);
    }

    private static List<ItemGetAllResDTO> getItemGetAllResDTOList(List<Item> itemList) {
        List<ItemGetAllResDTO> dtoList = new ArrayList<>();
        for (Item item : itemList) {
            dtoList.add(ItemGetAllResDTO.builder()
                    .itemId(item.getId())
                    .itemName(item.getName())
                    .originalPrice(item.getOriginalPrice())
                    .discountPrice(item.getDiscountPrice())
                    .stock(item.getStock())
                    .build());
        }
        return dtoList;
    }
}
