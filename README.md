## 一、DongBB项目宗旨
**做开发文档最详细的基础权限管理系统**。

文档地址：https://www.kancloud.cn/hanxt/vue-spring/content
文档中带演示项目地址。

## 二、DongBB项目核心特点
* 角色权限控制精确到：**可数据库配置的“数据接口访问权限”**，大部分的开源框架只精确到“菜单层面”。
* **标准的RBAC权限管理**，基于角色的权限管理。
* **只有JWT，没有Session**。只有最简JWT认证授权状态管理，**开发过程不使用任何session**、更没有redis集中session。面向那些希望节省服务端部署资源，并且希望灵活实现分布式扩展的应用场景。
* **只用element-ui**，不引入其他对element-ui二次封装的UI框架，保证灵活性，降低技术栈学习成本。跟随社区成长、兼容。
* 统一的接口数据结构、全局的异常处理
* 使用mybatis代码自动生成，针对单表的数据操作不写SQL

> 后续我还会在此版本的基础上开发，DongBB-cloud（Spring Cloud版本）。面向的应用场景将会有所不同。


## 三、模块说明
* dongbb-commons 通用异常处理以及通用响应数据结构等内容
* dongbb-pesistence 项目持久层代码，主要是RBAC权限管理模型对应的持久层(Mybatis plus实现)
* dongbb-security-jwt 系统内权限管理核心模块（基于JWT + Spring Security）
* dongbb-sql  sql建表及示例数据
* front-vue  前端应用（独立部署）
* server-jwt 后端Spring Boot应用（独立部署）

## 四、前端技术选型
|软件包|版本|
|---|---|
|vue|2.6.10|
|vue-router|3.1.3|
|vuex|3.1.2|
|@vue/cli|4.1.0|
|element-ui|2.13.0|

## 五、后端技术选型
|软件包|版本|
|---|---|
|jdk|8|
|spring-boot-starter-parent|2.2.4.RELEASE|
|spring security|5.2.1.RELEASE|
|mybatis-spring-boot-starter|1.3.2|
|mybatis|3.4.6|
|mybatis plus| 3.1.2|
|lombok|1.18.10|

## 六、重要更新历史
* 2020-08-25 commit：d429b347，使用Mybatis Plus替换了 Mybatis Generator。并对数据库表做了调整，因为ID采用Mybatis plus默认雪花算法，所以去掉自增，并修改ID字段为 BiGInt其其他对应的变化。为了方便大家学习，我没删掉Mybatis generator 代码，只是注释掉了！


## 七、为什么叫DongBB
开发此系统耗费了我大量的精力，如果只是开发对于我还好比较好做，我还要一边开发一边写“我是怎么开发出来的”，“怎么设计的”，一步一步的记录。这个精力耗费是巨大的，我的儿子“东东”也在一点点的成长，对于家人我感到有亏欠，我花费了大量应该去陪伴他们的时间来做这项工作。所以，它叫“Dong Baby”，简写DongBB。