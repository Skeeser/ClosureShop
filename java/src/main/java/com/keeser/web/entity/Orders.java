package com.keeser.web.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;


@Entity  // 表示这是一个实体类
@Getter  // 千万注意不能用@Data, 要不然to_string相互调用会栈溢出
@Setter
@AllArgsConstructor  // 生成全参数构造函数
@Table(name = "sp_order")  // 指定表名
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    private int userId;
    private String orderNumber;
    private double orderPrice;
    private char orderPay;
    private char isSend;
    private String tradeNo;
    private String orderFapiaoTitle;
    private String orderFapiaoCompany;
    private String orderFapiaoContent;
    private String consigneeAddr;
    private char payStatus;
    private Long createTime;
    private Long updateTime;

    @OneToOne(mappedBy = "orders")
    private OrdersGoods ordersGoods;


    public Orders(){
        orderNumber = generateOrderNumber();
        orderPay = '0';  // 默认未支付
        isSend = '否';
        orderFapiaoTitle = "个人";
        payStatus = '0';
        createTime = new Date().getTime() / 1000;
        updateTime = new Date().getTime() / 1000;
    }

    private String generateOrderNumber(){
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();

        // 生成随机数作为一部分订单号
        int random = (int) (Math.random() * 10000);

        // 使用UUID作为另一部分订单号
        String uniqueID = UUID.randomUUID().toString();

        // 组合时间戳、随机数和UUID来生成订单号
        String orderNumber = "ORDER-" + timestamp + "-" + random + "-" + uniqueID;
        return orderNumber;
    }
}
