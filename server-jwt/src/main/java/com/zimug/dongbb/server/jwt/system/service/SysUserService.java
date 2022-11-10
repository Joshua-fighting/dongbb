package com.zimug.dongbb.server.jwt.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zimug.dongbb.persistence.system.mapper.MySystemMapper;
import com.zimug.dongbb.persistence.system.mapper.SysUserMapper;
import com.zimug.dongbb.persistence.system.model.SysUser;
import com.zimug.dongbb.persistence.system.model.SysUserOrg;
import com.zimug.dongbb.server.jwt.config.DbLoadSysConfig;
import com.zimug.jwt.utils.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

import static com.zimug.commons.cachekey.RBACCacheKey.USER_DETAIL;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private MySystemMapper mySystemMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private DbLoadSysConfig dbLoadSysConfig;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    //根据登录用户名查询用户信息
    public SysUser getUserByUserName(String userName){
      Assert.isTrue(StringUtils.isNotEmpty(userName),
        "查询参数用户名不存在");

      SysUser sysUser = sysUserMapper.selectOne(
        new QueryWrapper<SysUser>().eq("username",userName));
      if(sysUser != null){
        sysUser.setPassword("");  //清空密码信息
      }
      return sysUser;
    }

    //用户管理：查询
    public IPage<SysUserOrg> queryUser(Long orgId ,
                                      String username ,
                                      String phone,
                                      String email,
                                      Boolean enabled,
                                      Date createStartTime,
                                      Date createEndTime,
                                      Integer pageNum,
                                      Integer pageSize){
      Page<SysUserOrg> page = new Page<> (pageNum,pageSize);   //查询第pageNum页，每页pageSize条数据
      if(StringUtils.isEmpty(username) && orgId == null){ //获取当前登录用户的orgId
        SysUser sysUser = sysUserMapper.selectOne(
          new QueryWrapper<SysUser>()
            .eq("username",jwtTokenUtil.getUsernameFromToken())
        );
        orgId = sysUser.getOrgId();
      }
      //查询orgId组织及其自组织的用户列表
      return mySystemMapper.selectUser(
                                    page,
                                    orgId,
                                    username,
                                    phone,email,enabled,
                                    createStartTime,
                                    createEndTime);
    }

    //用户管理：新增
    public void addUser(SysUser sysuser){
      sysuser.setPassword(passwordEncoder.encode(
        dbLoadSysConfig.getConfigItem("user.init.password")
      ));
      sysuser.setEnabled(true); //新增用户激活
      sysUserMapper.insert(sysuser);
    }

    //用户管理：修改
    @CacheEvict(value = USER_DETAIL,key = "#sysuser.getUsername()")
    public void updateUser(SysUser sysuser){
      Assert.isTrue(sysuser.getUsername() != null,
        "修改操作必须带用户名");
      //根据用户名修改用户信息
      LambdaQueryWrapper<SysUser> lambdaQ = Wrappers.lambdaQuery();
      lambdaQ.eq(SysUser::getUsername, sysuser.getUsername());
      sysUserMapper.update(sysuser,lambdaQ);
    }

    //用户管理：删除
    @CacheEvict(value = USER_DETAIL,key = "#username")
    public void deleteUser(String username){
      Assert.isTrue(StringUtils.isNotEmpty(username), "删除操作必须带主键");

      //根据用户名删除用户信息
      LambdaQueryWrapper<SysUser> lambdaQ = Wrappers.lambdaQuery();
      lambdaQ.eq(SysUser::getUsername, username);
      sysUserMapper.delete(lambdaQ);
    }

    //用户管理：重置密码
    @CacheEvict(value = USER_DETAIL,key = "#username")
    public void pwdreset(String username){
      Assert.isTrue(StringUtils.isNotEmpty(username), "重置密码操作必须带主键");

      SysUser sysUser = new SysUser();
      sysUser.setPassword(passwordEncoder.encode(
        dbLoadSysConfig.getConfigItem("user.init.password")
      ));

      //根据用户名修改用户信息
      LambdaQueryWrapper<SysUser> lambdaQ = Wrappers.lambdaQuery();
      lambdaQ.eq(SysUser::getUsername, username);
      sysUserMapper.update(sysUser,lambdaQ);
    }

    //用户管理：更新用户的激活状态
    @CacheEvict(value = USER_DETAIL,key = "#username")
    public void updateEnabled(String username,Boolean enabled){
      Assert.isTrue(StringUtils.isNotEmpty(username), "修改操作必须带主键");
      SysUser sysUser = new SysUser();
      sysUser.setEnabled(enabled);

      //根据用户名修改用户信息
      LambdaQueryWrapper<SysUser> lambdaQ = Wrappers.lambdaQuery();
      lambdaQ.eq(SysUser::getUsername, username);
      sysUserMapper.update(sysUser,lambdaQ);
    }

    //个人中心：修改密码
    @CacheEvict(value = USER_DETAIL,key = "#username")
    public void changePwd(String username,String oldPass,String newPass){

      SysUser sysUser = sysUserMapper.selectOne(
        new QueryWrapper<SysUser>().eq("username",username));
      //判断旧密码是否正确
      boolean isMatch = passwordEncoder.matches(oldPass,sysUser.getPassword());
      Assert.isTrue(isMatch, "原密码输入错误，请确认后重新输入！");

      SysUser sysUserNew = new SysUser();
      sysUserNew.setId(sysUser.getId());
      sysUserNew.setPassword(passwordEncoder.encode(newPass));
      sysUserMapper.updateById(sysUserNew);
    }

    //判断当前登录的用户密码是否是默认密码，如果是会让他去修改
    public Boolean isdefault(String username){
      SysUser sysUser = sysUserMapper.selectOne(
        new QueryWrapper<SysUser>().eq("username",username));

      //判断数据库密码是否是默认密码
      return passwordEncoder.matches(
        dbLoadSysConfig.getConfigItem("user.init.password"),
        sysUser.getPassword());
    }

}
