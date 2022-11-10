package com.zimug.jwt.service;

import com.zimug.jwt.mapper.MyUserDetailsServiceMapper;
import com.zimug.jwt.model.JwtProperties;
import com.zimug.jwt.model.MyUserDetails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.zimug.commons.cachekey.RBACCacheKey.*;

@Component("rabcService")
public class MyRBACService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    /**
     * 判断某用户是否具有该request资源的访问权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){

        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails){

            UserDetails userDetails = ((UserDetails)principal);
            List<GrantedAuthority> authorityList =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(request.getRequestURI());
            return userDetails.getAuthorities().contains(authorityList.get(0))
                    || jwtProperties.getDevOpeningURI().contains(request.getRequestURI());
        }

        return false;
    }


    @Cacheable(value = USER_DETAIL,key = "#username")
    public MyUserDetails findByUserName(String username) {
      return myUserDetailsServiceMapper.findByUserName(username);
    }

    @Cacheable(value = ROLE_CODES,key = "#username")
    public List<String> findRoleByUserName(String username) {
      return myUserDetailsServiceMapper.findRoleByUserName(username);
    }

    @Cacheable(value = API_URLS,key = "#roleCode")
    public List<String> findApiByRoleCode(String roleCode) {
      return myUserDetailsServiceMapper.findApiByRoleCode(roleCode);
    }

}
