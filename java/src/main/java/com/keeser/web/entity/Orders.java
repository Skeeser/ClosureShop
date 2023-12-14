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
    private String isSend;
    private String tradeNo;
    private String orderFapiaoTitle;
    private String orderFapiaoCompany;
    private String orderFapiaoContent;
    private String consigneeAddr;
    private char payStatus;
    private Long createTime;
    private Long updateTime;
}
