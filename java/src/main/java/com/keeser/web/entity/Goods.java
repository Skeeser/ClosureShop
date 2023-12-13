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
@Table(name = "sp_goods")  // 指定表名
// jpa生成的无需json化的属性, 忽略掉
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Goods {
    // 定义主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private int goodsId;

    private String goodsName;
    private double goodsPrice;
    private int goodsNumber;
    private int goodsWeight;
    private int catId;
    private String goodsIntroduce;
//    private String goodsBigLogo;
//    private String goodsSmallLogo;
    private int isDel;
    private int addTime;
    private int updTime;
//    private Timestamp deleteTime;
    private int catOneId;
    private int catTwoId;
    private int catThreeId;
    private int hotNumber;
    private int isPromote;
    private int goodsState;


}
