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
    server.port=8888
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
项目完成记得开源  

### spring连接数据库
ssh -f <user>@<jump_host_ip> -L <local_host_ip>:<arbitrary_local_port>:<db_host_ip>:<db_port> -N -i <private_key>
and example would be

> ssh转发
ssh -f keeser@47.236.36.97 -L 127.0.0.1:3309:127.0.0.1:3306 -N 

### token
参考 
https://www.cnblogs.com/itdragon/p/12637923.html  
https://www.cnblogs.com/loveer/p/11429111.html  
https://blog.csdn.net/weixin_42531072/article/details/130239087  
https://blog.csdn.net/qq_50969362/article/details/134100542  

### todos
- [ ] 每次发送请求都进行token验证解析  
