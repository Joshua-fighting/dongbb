package com.zimug.jwt.config;


import com.zimug.jwt.model.JwtProperties;
import com.zimug.jwt.service.JwtAuthService;
import com.zimug.jwt.service.MyUserDetailsService;
import com.zimug.jwt.utils.JWTConstants;
import com.zimug.jwt.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * Spring Security 配置
 * 可以配置多个WebSecurityConfigurerAdapter
 * 但是多个Adaptor有执行顺序，默认值是100
 * 这里设置为1会优先执行
 */
@Configuration
@Order(1)
public class JwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private String[] PERMIT_API_LIST = {
      JWTConstants.CONTROLLER_AUTHENTICATION,
      JWTConstants.CONTROLLER_REFRESH,
      JWTConstants.CONTROLLER_ROLES
    };

    private String[] PERMIT_RES_LIST = {
      "/swagger-ui/swagger-resources/**",
      "/swagger-resources/**",
      "/webjars/**",
      "/swagger-ui/**",
      "/v2/api-docs",
      "/v3/api-docs",
    };


    @Override
    public void configure(HttpSecurity http) throws Exception {
        if(jwtProperties.getCsrfDisabled()){
            http = http.csrf().disable();
        }
        http.cors()
            .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers(PERMIT_API_LIST).permitAll();

        //通过配置实现的不需要JWT令牌就可以访问的接口
        for(String uri : jwtProperties.getPermitAllURI()){
          http.authorizeRequests().antMatchers(uri).permitAll();
        }
        //RBAC权限控制级别的接口权限校验
        http.authorizeRequests().anyRequest()
          .access("@rabcService.hasPermission(request,authentication)");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
      //将项目中静态资源路径开放出来
      web.ignoring().antMatchers(PERMIT_RES_LIST);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 跨站资源共享配置
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(jwtProperties.getCorsAllowedOrigins());
        configuration.setAllowedMethods(jwtProperties.getCorsAllowedMethods());
        configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthService jwtAuthService(JwtTokenUtil jwtTokenUtil) throws Exception {
        return new JwtAuthService(
                this.authenticationManagerBean(),jwtTokenUtil);
    }


}
