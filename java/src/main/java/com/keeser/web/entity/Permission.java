package com.keeser.web.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


@Entity  // 表示这是一个实体类
@Getter  // 千万注意不能用@Data, 要不然to_string相互调用会栈溢出
@Setter
@NoArgsConstructor  // 生成无参构造函数
@AllArgsConstructor  // 生成全参数构造函数
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
    private String psApiPath;  // 权限路径
//    @OneToOne(mappedBy = "permission",fetch = FetchType.EAGER)
//    private PermissionApi permissionApi;

}
