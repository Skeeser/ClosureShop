package com.keeser.web.dao;


import com.keeser.web.entity.Goods;
import com.keeser.web.entity.Orders;
import org.apache.logging.log4j.util.OsgiServiceLocator;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EntityScan("com.keeser.web.entity.Orders")
public interface OrdersDAO extends JpaRepository<Orders, Integer> {

    int countAllByOrderNumberLike(String query);
    int countAllByUserIdAndIsCompleteOrderAndOrderNumberLike(int user_id, char isCompleteOrder, String query);

    List<Orders> findAllByOrderNumberLike(String query, Pageable pageable);
    List<Orders> findAllByUserIdAndIsCompleteOrderAndOrderNumberLike(int user_id, char isCompleteOrder, String query);

    Orders findByOrderId(int order_id);
    Orders findByUserIdAndIsCompleteOrder(int user_id,  char ch);
}
