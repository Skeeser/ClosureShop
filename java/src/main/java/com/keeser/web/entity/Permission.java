package com.keeser.web.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity  // 表示这是一个实体类
@Table(name = "sp_manager")  // 指定表名
// jpa生成的无需json化的属性, 忽略掉
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Permission {
}
