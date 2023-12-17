package com.keeser.web.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


@Entity  // 表示这是一个实体类
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sp_permission_api")  // 指定表名
// jpa生成的无需json化的属性, 忽略掉
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class PermissionApi {
    // 定义主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // 不要用默认的懒加载, 不然会很慢
//    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    @JoinColumn(name = "ps_id")
    private int psId;  // 权限id
//    private Permission permission;

    private String psApiPath;  // 权限路径


}
