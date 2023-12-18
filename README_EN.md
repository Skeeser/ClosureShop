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

ClosureShop E-commerce Platform :smile::smile::smile:

A project for Java Web Course, named after Arknights :stuck_out_tongue_closed_eyes::stuck_out_tongue_closed_eyes::stuck_out_tongue_closed_eyes:

Frontend implemented with VUE2.0, backend with spring technology.

If you find it helpful, please give it a star :wink:​

Artist: \_QuAn\_ (deleted upon request)

<br>

</div>

<br>

## Website
### Website URL
> Due to the server being overseas, accessing it via VPN may be faster

**URL:** http://47.236.36.97:9200/admin/  

### Sales Admin Account

**Test Account:**  admin  

**Password:** 123456

### Customer Test Account
> You can also register a new account

**Test Account:**  Alan  

**Password:** 123456

<br>

## Features

### Customer

- **Registration**, **Login**, **Logout**
- User purchase process (Browsing/Searching store -> Adding to cart -> Placing order -> Payment)
- Searching, viewing, deleting user orders

### Sales

- **Management** of product catalog (including basic operations such as adding, deleting, modifying, fuzzy searching, etc.)
- Log of browsing/purchasing by customers
- Backend sales statistics report, sales status

<br>

## File Structure
├─assets => Stores static resources  
├─vuejs => Stores frontend code  
├─java => Stores backend code  
└─doc => Stores development documents  

<br>

## Software Architecture

Browser/Server Architecture

<br>

## System Architecture

Linux, Windows

<br>

## Dependency Check
- vue
- maven
- spring
- jdk17
- nginx

<br>

## Build
### Frontend
- Install project dependencies
```shell
npm install --registry=https://registry.npmmirror.com
```
- Compile and hot-reload for development
```shell
npm run dev
```
- Compile and minify for production
```shell
npm run build
```
- Lint and fix files
```shell
npm run lint
```

### Backend
After configuring the maven environment

Execute the following command to package and generate a jar file  
```shell
mvn package
```

<br>

## Deployment and Running
### Frontend
Use nginx for reverse proxy  
Place the built Vue project in nginx's html directory  
Restart nginx

### Backend
Run the jar file using Java  
```shell
java -Djdk.util.jar.enableMultiRelease=false -jar Web-0.0.1-SNAPSHOT.jar
```

<br>

## User Guide

Simply access the login website

If unable to access, you can deploy it on your server  

<br>

## How to Contribute

If you happen to see this project and want to participate in development

You can refer to this document [How to Contribute to Open Source Projects](doc/github参与开源项目流程.md)  

As well as [Development Technical Documentation](doc/dev.md)  

And [API Interface Documentation](doc/api接口文档.md)  

<br>

## About the Author

keeser

<br>

## TODO

- [x] Set up frontend
- [x] Build backend environment
- [x] Develop frontend interface
- [x] Develop corresponding backend features
- [x] Deploy frontend
- [x] Deploy backend

<br>

## License

MPL 2.0
