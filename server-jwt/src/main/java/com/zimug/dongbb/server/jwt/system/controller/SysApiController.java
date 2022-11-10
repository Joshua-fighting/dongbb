package com.zimug.dongbb.server.jwt.system.controller;


import com.zimug.commons.exception.AjaxResponse;
import com.zimug.dongbb.persistence.system.model.SysApi;
import com.zimug.dongbb.server.jwt.system.model.RoleCheckedIds;
import com.zimug.dongbb.server.jwt.system.model.SysApiNode;
import com.zimug.dongbb.server.jwt.system.service.SysApiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sysapi")
public class SysApiController {

  @Resource
  private SysApiService sysapiService;

  //接口管理:查询
  @PostMapping(value = "/tree")
  public List<SysApiNode> tree(@RequestParam("apiNameLike") String apiNameLike,
                               @RequestParam("apiStatus") Boolean apiStatus) {

    return sysapiService.getApiTree(apiNameLike, apiStatus);
  }

  //接口管理:修改
  @PostMapping(value = "/update")
  public AjaxResponse update(@RequestBody SysApi sysApi) {
    sysapiService.updateApi(sysApi);
    return AjaxResponse.success("更新接口配置成功！");
  }

  //接口管理:新增
  @PostMapping(value = "/add")
  public AjaxResponse add(@RequestBody SysApi sysApi) {
    sysapiService.addApi(sysApi);
    return AjaxResponse.success("新增接口配置成功！");
  }

  //接口管理:删除
  @PostMapping(value = "/delete")
  public AjaxResponse delete(@RequestBody SysApi sysApi) {
    sysapiService.deleteApi(sysApi);
    return AjaxResponse.success("删除接口配置成功!");
  }

  //角色管理：API树展示（勾选项、展开项）
  @PostMapping(value = "/checkedtree")
  public Map<String,Object> checkedtree(@RequestParam Long roleId) {
    Map<String,Object> ret = new HashMap<>();
    ret.put("tree",sysapiService.getApiTree("",null));
    ret.put("expandedKeys",sysapiService.getExpandedKeys());
    ret.put("checkedKeys",sysapiService.getCheckedKeys(roleId));
    return ret;
  }

  //角色管理：保存API权限勾选结果
  @PostMapping(value = "/savekeys")
  public AjaxResponse savekeys(@RequestBody RoleCheckedIds roleCheckedIds) {
    sysapiService.saveCheckedKeys(
      roleCheckedIds.getRoleCode(),
      roleCheckedIds.getRoleId(),
      roleCheckedIds.getCheckedIds()
    );
    return AjaxResponse.success("保存接口权限成功!");
  }

  //接口管理：更新接口禁用状态
  @PostMapping(value = "/status/change")
  public AjaxResponse update(@RequestParam Long apiId,
                             @RequestParam Boolean status) {
    sysapiService.updateStatus(apiId, status);
    return AjaxResponse.success("接口禁用状态更新成功！");
  }
}
