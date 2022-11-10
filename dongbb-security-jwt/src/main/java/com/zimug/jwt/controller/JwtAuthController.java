package com.zimug.jwt.controller;


import com.zimug.commons.exception.AjaxResponse;
import com.zimug.commons.exception.CustomException;
import com.zimug.commons.exception.CustomExceptionType;
import com.zimug.jwt.mapper.MyUserDetailsServiceMapper;
import com.zimug.jwt.model.JwtProperties;
import com.zimug.jwt.service.JwtAuthService;
import com.zimug.jwt.utils.JWTConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * JWT获取令牌和刷新令牌接口
 */
@RestController
@ConditionalOnBean({JwtAuthService.class})
@ConditionalOnProperty(name = "zimug.jwt.useDefaultController",
                        havingValue = "true")
public class JwtAuthController {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private JwtAuthService jwtAuthService;

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    /**
     * 使用用户名密码换JWT令牌
     */
    @RequestMapping(value = JWTConstants.CONTROLLER_AUTHENTICATION)
    public AjaxResponse login(@RequestBody Map<String,String> map){

        String username  = map.get(jwtProperties.getUserParamName());
        String password = map.get(jwtProperties.getPwdParamName());

        if(StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)){
            return AjaxResponse.error(
                    CustomExceptionType.USER_INPUT_ERROR,
                    "用户名或者密码不能为空");
        }
        try {
            return AjaxResponse.success(
                    jwtAuthService.login(username, password,null));
        }catch (CustomException e){
            return AjaxResponse.error(e);
        }
    }

    /**
     * 刷新JWT令牌
     */
    @RequestMapping(value = JWTConstants.CONTROLLER_REFRESH)
    public  AjaxResponse refresh(@RequestHeader("${zimug.jwt.header}") String token){
            return AjaxResponse.success(jwtAuthService.refreshToken(token));
    }


    /**
     * 获取用户角色列表接口
     */
    @RequestMapping(value = JWTConstants.CONTROLLER_ROLES)
    public  AjaxResponse roles(
      @RequestHeader("${zimug.jwt.header}") String token){
      return AjaxResponse.success(jwtAuthService.roles(token));
    }

}
