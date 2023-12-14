package com.keeser.web.dao;

import com.keeser.web.entity.Goods;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


@EntityScan("com.keeser.web.entity.Goods")
public interface GoodsDAO extends JpaRepository<Goods, Integer> {

    // 模糊搜索商品数量
    int countAllByGoodsNameLike(String query);

    // 限定范围获取对象
    // 用limit参数传不进去, 用自带的分页查询
    //    @Query(nativeQuery = true,
    //            value = "SELECT * FROM sp_goods " +
    //                    "WHERE goods_name LIKE %?1% " +
    //                    "LIMIT :#{#offset},:#{#pagesize}; ")
    ArrayList<Goods> findAllByGoodsNameLike(String query,
                                              Pageable pageable);

    Goods findGoodsByGoodsId(int goodId);

}
