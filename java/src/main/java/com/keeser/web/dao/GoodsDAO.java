package com.keeser.web.dao;

import com.keeser.web.entity.Goods;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@EntityScan("com.keeser.web.entity.Goods")
public interface GoodsDAO extends JpaRepository<Goods, Integer> {

    // 模糊搜索商品数量
    List<Goods> findAllByGoodsNameLike(String query);
}
