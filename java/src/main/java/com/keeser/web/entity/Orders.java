package com.keeser.web.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
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

    private int userId;  // 唯一要设置的变量
    private String orderNumber;  // 1
    private double orderPrice;  // 1
    private char orderPay;  // 1
    private char isSend;  // 1
    private String tradeNo;  // 1
    private String orderFapiaoTitle;  // 1
    private String orderFapiaoCompany;  // 1
    private String orderFapiaoContent;  // 1
    private String consigneeAddr;  // 1
    private char payStatus;  // 1
    private Long createTime;  // 1
    private Long updateTime;  // 1
    // 是否结束订单
    private char isCompleteOrder;


    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrdersGoods> ordersGoodsList;


    public Orders(){
        orderNumber = generateOrderNumber();
        orderPrice = 0.0;
        orderPay = '0';  // 默认未支付
        isSend = '否';
        orderFapiaoTitle = "个人";
        orderFapiaoCompany = "";
        orderFapiaoContent = "";
        consigneeAddr = "";
        tradeNo = "";
        payStatus = '0';
        isCompleteOrder = '否';
        createTime = new Date().getTime() / 1000;
        updateTime = new Date().getTime() / 1000;
    }

    private String generateOrderNumber(){

        // 使用UUID作为另一部分订单号
        String uniqueID = UUID.randomUUID().toString().replace("-", "");;

        return uniqueID;
    }
}
