package com.ssg.market.domain.reset.service;

import com.ssg.market.data.model.entity.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResetService {

    private final EntityManager em;

    public void reset() {
        em.createQuery("DELETE FROM ORDER_ITEMS ").executeUpdate();
        em.createQuery("DELETE FROM ORDERS ").executeUpdate();
        em.createQuery("DELETE FROM ITEMS ").executeUpdate();

        List<Item> items = List.of(
                new Item(1000000001L, "이마트 생수", 800, 100, 1000),
                new Item(1000000002L, "신라면 멀티팩", 4200, 500, 500),
                new Item(1000000003L, "바나나 한 송이", 3500, 300, 200),
                new Item(1000000004L, "삼겹살 500g", 12000, 2000, 100),
                new Item(1000000005L, "오리온 초코파이", 3000, 400, 300)
        );

        items.forEach(em::persist);
    }
}
