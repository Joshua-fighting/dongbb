## 用途
* Spring Boot web项目快速初始化

## package(zom.zimug.commons)
* exception 异常处理包
    * 封装JSON接口统一数据响应结果AjaxResponse
    * 全局异常处理WebExceptionHandler
    * 自定义异常CustomException
    * 自定义异常枚举，防止团队程序员发散胡乱创建多种异常，CustomExceptionType
    * 针对HTTP Request的统一处理类GlobalRequestAdvice
    * 针对HTTP Response的统一处理类GlobalResponseAdvice
    * ModelViewXxxxxx系列是针对页面跳转出现异常时的处理，使用AOP面向切面编程，与JSON统一响应数据接口AjaxResponse不冲突   
* model 通用实体包
    * tree 包含构造Element-ui对应的树形机构的实体及树形数据构造工具类
    
    
## 文档
exception 异常处理的实现参考
[《手摸手教你学Spring Boot 2.x》](https://www.kancloud.cn/hanxt/springboot2/content)
 第八章：《统一全局异常处理》实现