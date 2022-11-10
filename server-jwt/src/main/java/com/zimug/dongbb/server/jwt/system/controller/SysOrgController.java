package com.zimug.dongbb.server.jwt.system.controller;


import com.zimug.commons.exception.AjaxResponse;
import com.zimug.dongbb.persistence.system.model.SysOrg;
import com.zimug.dongbb.persistence.system.model.SysUser;
import com.zimug.dongbb.server.jwt.system.model.SysOrgNode;
import com.zimug.dongbb.server.jwt.system.service.SysOrgService;
import com.zimug.dongbb.server.jwt.system.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sysorg")
public class SysOrgController {

  @Resource
  private SysOrgService sysorgService;
  @Resource
  private SysUserService sysuserService;

  @PostMapping(value = "/tree")
  public List<SysOrgNode> tree(@RequestParam("username") String username,
                               @RequestParam("orgNameLike") String orgNameLike,
                               @RequestParam("orgStatus") Boolean orgStatus) {
    SysUser sysUser = sysuserService.getUserByUserName(username);
    return sysorgService.getOrgTreeById(sysUser.getOrgId(), orgNameLike, orgStatus);
  }

  @PostMapping(value = "/update")
  public AjaxResponse update(@RequestBody SysOrg sysOrg) {
    sysorgService.updateOrg(sysOrg);
    return AjaxResponse.success("更新组织机构成功！");
  }

  @PostMapping(value = "/add")
  public AjaxResponse add(@RequestBody SysOrg sysOrg) {
    sysorgService.addOrg(sysOrg);
    return AjaxResponse.success("新增组织机构成功！");
  }


  @PostMapping(value = "/delete")
  public AjaxResponse delete(@RequestBody SysOrg sysOrg) {
    sysorgService.deleteOrg(sysOrg);
    return AjaxResponse.success("删除组织机构成功!");
  }

  //组织管理：更新组织禁用状态
  @PostMapping(value = "/status/change")
  public AjaxResponse update(@RequestParam Long orgId,
                             @RequestParam Boolean status) {
    sysorgService.updateStatus(orgId, status);
    return AjaxResponse.success("组织禁用状态更新成功！");
  }
}
