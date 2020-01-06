## shop

### 简介

Java web小型零售超市管理系统（软件综合实习、数据库综合实习必备良品~）。用户分为三种：销售人员、库存人员、管理员。功能模块包括：登陆与注册、前台收银、库存管理、员工管理、销售统计。

主要用到的知识点：`jsp, servlet, sqlserver, html/css/js, easyui, jquery, ajax`



- **前端：**主要使用easyui + jquery前端框架快速搭建
- **后端:**   没有使用框架，实际上jsp也没用，主要使用ajax请求servlet获取数据并返回给前端（前后端分离）

- **数据库:**SQL Server 2017
  - `staff`-员工表
  - `goods`-商品表
  - `sale`-销售记录表
  - `pro`-供应商表
  - `saleDetail`-销售详情表
- **运行环境：**java版本：`jdk-13`，tomcat服务器版本：`Apache Tomcat v7.0`
- **用到的第三方jar包：**
  - `mssql-jdbc-7.0.0.jre10.jar` （用来连接SQL Server数据库）
  - `jstl-1.2.jar` (jstl标签库，本项目用的不多)
  - `gson-2.8.6.jar`    (处理json)
  - `fastjson-1.2.62.jar` (处理json，不过是阿里的，感觉挺好用的)



### 系统截图

- 登录界面

![file:///C:/Users/caiso/eclipse-workspace/shop/WebContent/source/test1.png](file:///C:/Users/caiso/eclipse-workspace/shop/WebContent/source/test1.png)

- 前台销售界面

![](file:///C:/Users/caiso/eclipse-workspace/shop/WebContent/source/test2.png)

- 商品管理界面

![](file:///C:/Users/caiso/eclipse-workspace/shop/WebContent/source/test3.png)

