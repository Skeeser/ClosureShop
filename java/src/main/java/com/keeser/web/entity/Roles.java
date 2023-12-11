package com.keeser.web.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity  // 表示这是一个实体类
@Getter  // 千万注意不能用@Data, 要不然to_string相互调用会栈溢出
@Setter
@Table(name = "sp_role")  // 指定表名
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    int roleId;

    String psIds;
    String roleDesc;

}
