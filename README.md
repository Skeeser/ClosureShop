<div align="center">

<img alt="LOGO" src="assets/closure.jpg" width="256" height="256" />

# ClosureShop

<br>

<div>
    <img alt="java" src="https://img.shields.io/badge/java-17-%2300599C">
    <img alt="vuejs" src="https://img.shields.io/badge/vuejs-2.x-blue">
</div>
<div>
    <img alt="platform" src="https://img.shields.io/badge/platform-Windows%20%7C%20Linux%20-blueviolet">
</div>
<div>
    <img alt="license" src="https://img.shields.io/github/license/Skeeser/ClosureShop">
    <img alt="commit" src="https://img.shields.io/github/commit-activity/m/Skeeser/ClosureShop?color=%23ff69b4">
    <img alt="stars" src="https://img.shields.io/github/stars/Skeeser/ClosureShop?style=social">
</div>
<br>

[简体中文](README.md) | [English](README_EN.md)

可露希尔电商平台 :smile::smile::smile:

Java Web 课程的项目, 项目名字来自明日方舟 :stuck_out_tongue_closed_eyes::stuck_out_tongue_closed_eyes::stuck_out_tongue_closed_eyes:

前端基于 VUE2.0 实现, 后端则是 spring 技术实现

如果觉得不错的话, 麻烦点个star吧 :wink:​   

画师: \_QuAn\_ (侵删)

<br>

</div>

<br>

## 网站
### 网站地址
> 由于服务器在海外, 翻墙可以访问快些 

**网址:** http://47.236.36.97:9200/admin/  

### 销售管理员账号

**测试账号:**  admin  

**密码:** 123456

### 顾客测试账号
> 也可以自己注册个新的账号

**测试账号:**  Alan  

**密码:** 123456

<br>

## 功能特性

### 顾客

- 用户的 **注册**，**登录**，**注销**
- 用户购买流程（浏览/查询商城 -> 添加至购物车 -> 添加订单 -> 付款）
- 搜索, 查看, 删除用户的订单

### 销售

- 商品目录的 **管理**（包括最基本的添加，删除，修改, 模糊搜索等操作）
- 客户的 浏览/购买 **日志** 记录
- 后台销售统计报表，销售状态

<br>

## 文件结构
├─assets => 存放静态资源  
├─vuejs => 存放前端代码  
├─java => 存放后端代码  
└─doc => 存放开发文档  

<br>

## 软件架构

Browser/Server Architecture

<br>

## 系统架构

Linux, Windows

<br>

## 依赖检查
- vue
- maven
- spring
- jdk17
- nginx

<br>

## 构建
### 前端
- 安装项目依赖
```shell
npm install --registry=https://registry.npmmirror.com
```
- 编译并在开发过程中进行热重载
```shell
npm run dev
```
- 编译并压缩用于生产环境
```shell
npm run build
```
- 检查并修复文件
```shell
npm run lint
```

### 后端
配置maven环境后  


执行下面命令打包生成jar  
```shell
mvn package
```

<br>

## 部署和运行
### 前端
使用nginx进行反向代理  
将build后的vue项目放在nginx的html目录下  
重启nginx即可


### 后端
使用java运行jar包  
```shell
java -Djdk.util.jar.enableMultiRelease=false -jar Web-0.0.1-SNAPSHOT.jar
```

<br>


## 使用指南

直接访问登录网站即可

如果访问不了, 则可以自己部署在服务器上  

<br>

## 如何贡献

如果你碰巧看见这个项目, 想要参与开发

可以查看这个文档 [如何参与开源项目](doc/github参与开源项目流程.md)  

以及[开发的技术文档](doc/dev.md)  

和[api接口文档](doc/api接口文档.md)  

<br>

## 关于作者

keeser

<br>

## TODO

- [x] 先搭好前端
- [x] 构建后端环境
- [x] 实现前端界面的开发
- [x] 后端对应功能的开发
- [x] 部署前端
- [x] 部署后端


<br>

## 许可证

MPL 2.0
