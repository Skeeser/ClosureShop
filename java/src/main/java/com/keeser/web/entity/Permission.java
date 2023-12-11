package com.keeser.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


@Entity  // 表示这是一个实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sp_permission")  // 指定表名
// jpa生成的无需json化的属性, 忽略掉
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Permission {
    // 定义主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ps_id")
    int psId;

    String psName;  // 权限名称
    int psPid;  // 父级id
    int psLevel;  // 权限等级

}
