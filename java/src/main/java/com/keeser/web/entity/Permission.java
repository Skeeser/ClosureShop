package com.keeser.web.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


@Entity  // 表示这是一个实体类
@Getter  // 千万注意不能用@Data, 要不然to_string相互调用会栈溢出
@Setter
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
    private int psId;

    private String psName;  // 权限名称
    private int psPid;  // 父级id
    private int psLevel;  // 权限等级

    @OneToOne(mappedBy = "permission")
    private PermissionApi permissionApi;

}
