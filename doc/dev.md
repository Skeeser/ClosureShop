# 开发文档

## 目录
[toc]

## 前端
### 框架
Vue

### 登录


<br>

## Web后端
Spring Boot

### 初始化项目
注意, spring内部默认用了Tomcat了, 就不用再配置了  

### 数据库
- 在服务器上启动mysql服务  
- spring配置
    - 参考已有项目的pom.xml , 配置依赖  
    - 配置完依赖后，还需要配置数据库。打开 src\main\resources\application.properties ，在原来的基础上，添加如下语句  
    ```
    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/white_jotter?characterEncoding=UTF-8
    spring.datasource.username=root
    spring.datasource.password=root
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.jpa.hibernate.ddl-auto = none
    ```
    注意端口、数据库名、用户名、密码要与你想使用的数据库一致。

### application.properties文件
因为涉及账号密码, 暂不开源
src/main/resources/application.properties  



