package com.zimug.dongbb.server.jwt.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zimug.commons.exception.AjaxResponse;
import com.zimug.dongbb.persistence.system.model.SysUser;
import com.zimug.dongbb.persistence.system.model.SysUserOrg;
import com.zimug.dongbb.server.jwt.system.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/sysuser")
public class SysUserController {

  @Resource
  private SysUserService sysuserService;

  //根据登录用户名查询用户信息
  @GetMapping(value = "/info")
  public SysUser info(@RequestParam("username") String username) {
      return sysuserService.getUserByUserName(username);
  }

  //用户管理：查询
  @PostMapping("/query")
  public IPage<SysUserOrg> query(@RequestParam("orgId") Long orgId ,
                                 @RequestParam("username") String username ,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("email") String email,
                                 @RequestParam("enabled") Boolean enabled,
                                 @RequestParam("createStartTime") Date createStartTime,
                                 @RequestParam("createEndTime") Date createEndTime,
                                 @RequestParam("pageNum") Integer pageNum,
                                 @RequestParam("pageSize") Integer pageSize) {

    return sysuserService.queryUser(orgId,username,phone,email,enabled,
                                    createStartTime, createEndTime,
                                    pageNum,pageSize);
  }

  //用户管理：更新
  @PostMapping(value = "/update")
  public AjaxResponse update(@RequestBody SysUser sysUser) {
    sysuserService.updateUser(sysUser);
    return AjaxResponse.success("更新用户成功！");
  }
  //用户管理：新增
  @PostMapping(value = "/add")
  public AjaxResponse add(@RequestBody SysUser sysUser) {
    sysuserService.addUser(sysUser);
    return AjaxResponse.success("新增用户成功！");
  }
  //用户管理：删除
  @PostMapping(value = "/delete")
  public AjaxResponse delete(@RequestParam String username) {
    sysuserService.deleteUser(username);
    return AjaxResponse.success("删除用户成功!");
  }
  //用户管理：重置密码
  @PostMapping(value = "/pwd/reset")
  public AjaxResponse pwdreset(@RequestParam String username) {
    sysuserService.pwdreset(username);
    return AjaxResponse.success("重置密码成功!");
  }
  //判断登录用户密码是否是默认密码
  @PostMapping(value = "/pwd/isdefault")
  public Boolean isdefault(@RequestParam String username) {

    return sysuserService.isdefault(username);
  }
  //修改密码
  @PostMapping(value = "/pwd/change")
  public AjaxResponse pwdchange(@RequestParam String username,
                                @RequestParam String oldPass,
                                @RequestParam String newPass) {
    sysuserService.changePwd(username,oldPass,newPass);
    return AjaxResponse.success("修改密码成功!");
  }

  //用户管理：更新用户激活状态
  @PostMapping(value = "/enabled/change")
  public AjaxResponse update(@RequestParam String username,
                             @RequestParam Boolean enabled) {
    sysuserService.updateEnabled(username, enabled);
    return AjaxResponse.success("用户状态更新成功！");
  }
}