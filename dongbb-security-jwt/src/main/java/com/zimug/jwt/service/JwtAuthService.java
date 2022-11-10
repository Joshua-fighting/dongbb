package com.zimug.jwt.service;


import com.zimug.commons.exception.CustomException;
import com.zimug.commons.exception.CustomExceptionType;
import com.zimug.jwt.mapper.MyUserDetailsServiceMapper;
import com.zimug.jwt.utils.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


public class JwtAuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    private JwtAuthService(){}

    public JwtAuthService(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * 登录认证换取JWT令牌
     * @return JWT
     */
    public String login(String username,
                        String password,
                        Map<String,String> payloads) throws CustomException {
        try {
            UsernamePasswordAuthenticationToken upToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (AuthenticationException e){
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR
                            ,"用户名或者密码输入错误,或者新建用户未赋予角色权限！");
        }

        return jwtTokenUtil.generateToken(username,payloads);
    }


    public String refreshToken(String oldToken){
        if(!jwtTokenUtil.isTokenExpired(oldToken)){
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return null;
    }

  /**
   * 获取角色信息列表
   * @param token
   * @return
   */
  public List<String> roles(String token){
    String username = jwtTokenUtil.getUsernameFromToken(token);
    //加载用户角色列表
    List<String> roleCodes =
      myUserDetailsServiceMapper.findRoleByUserName(username);
    return roleCodes;
  }

}
