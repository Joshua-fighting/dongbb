## 功能
JWT Spring Boot Starter，通过简单地配置就可以实现JWT认证功能。


## 配置自定义
如以下配置内容都可以自定义：
~~~

zimug:
  jwt:
    enabled: true   # JWT功能开关
    secret: fjkfaf;afa   # 服务端密钥（自定义）
    expiration: 3600000 # JWT超时时间
    header: JWTHeaderName # JWT通过header传输，Header名字
    userParamName: username # 登录认证用户名的JSON属性名称
    pwdParamName: password # 登录认证密码的JSON属性名称（参考下面接口）
    useDefaultController: true # 是否使用默认的登录认证Controller，可以仿造代码中JwtAuthController自定义
    corsAllowedOrigins:  #允许跨域访问的前端域
      - http://xxx.com
      - http://localhost:8080
    corsAllowedMethods: # 允许哪些HTTP方法跨域
      - GET
      - POST
    devOpeningURI: # 在开发过程中临时开放访问的接口，不受jwt鉴权
    permitAllURI: # 允许直接访问的路径，不受jwt鉴权。如：login.html


~~~

## 登录认证接口
### 请求
接口地址请求："/authentication"，接口采用JSON的数据格式。默认的用户名参数名称是username，密码的参数名称是password。可以在application全局配置文件中，根据zimug.jwt.userParamName和zimug.jwt.pwdParamName调整参数名称。

~~~
{
    username:"",
    password:""
}
~~~
### 响应
采用通用的AjaxResponse，在《【后端】统一接口响应数据结构》中定义的数据结构。如下是正确登录认证之后的返回结果。
~~~
{
    "isok": true,
    "code": 200,
    "message": "success",
    "data": "JWT令牌字符串"
}
~~~

## 令牌刷新接口
用旧的令牌换新的令牌，通过Header传递数据
~~~
/**
 * 刷新JWT令牌
 */
@RequestMapping(value = JWTConstants.CONTROLLER_REFRESH)
public  AjaxResponse refresh(@RequestHeader("${zimug.jwt.header}") String token){
        return AjaxResponse.success(jwtAuthService.refreshToken(token));
}
~~~

## 知识基础
本项目实现已经整理为参考文档
[《Spring Security-JWT-OAuth2一本通(基于SpringBoot2.0)》](https://www.kancloud.cn/hanxt/springsecurity/content) ,参考前三章内容即可实现.



