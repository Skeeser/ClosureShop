package com.keeser.web.dao;


import com.keeser.web.entity.Orders;
import com.keeser.web.entity.OrdersGoods;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;

@EntityScan("com.keeser.web.entity.OrdersGoods")
public interface OrdersGoodsDAO extends JpaRepository<OrdersGoods, Integer> {
}
