package com.keeser.web.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity  // 表示这是一个实体类
@Getter  // 千万注意不能用@Data, 要不然to_string相互调用会栈溢出
@Setter
@NoArgsConstructor  // 生成无参构造函数
@AllArgsConstructor  // 生成全参数构造函数
@Table(name = "sp_order_goods")  // 指定表名
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class OrdersGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    // 应该做成多对一的, 嫌麻烦就不做了
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    // private int orderId;
    Orders orders;

    private int goodsId;
    private double goodsPrice;
    private int goodsNumber;
    private double goodsTotalPrice;



}
