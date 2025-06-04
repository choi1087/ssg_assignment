package com.ssg.market.data.repository;

import com.ssg.market.data.model.entity.Item;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM ITEMS i WHERE i.id IN :itemIdList")
    List<Item> findByIdInForUpdateStock(@Param("itemIdList") List<Long> itemIdList);
}
