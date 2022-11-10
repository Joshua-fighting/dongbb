package com.zimug.dongbb.server.jwt.system.controller;


import com.zimug.commons.exception.AjaxResponse;
import com.zimug.dongbb.persistence.system.model.SysConfig;
import com.zimug.dongbb.server.jwt.config.DbLoadSysConfig;
import com.zimug.dongbb.server.jwt.system.service.SysConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sysconfig")
public class SysConfigController {

  @Resource
  private DbLoadSysConfig dbLoadSysConfig;

  @PostMapping(value = "/all")
  public List<SysConfig> all() {
    return dbLoadSysConfig.getSysConfigList();
  }

  @PostMapping(value = "/refresh")
  public List<SysConfig> refresh() {
    return dbLoadSysConfig.getSysConfigList();
  }


  @Resource
  private SysConfigService sysconfigService;

  @PostMapping(value = "/query")
  public List<SysConfig> query(@RequestParam("configLike") String configLike) {
    return sysconfigService.queryConfigs(configLike);
  }

  @PostMapping(value = "/update")
  public AjaxResponse update(@RequestBody SysConfig sysConfig) {
    sysconfigService.updateConfig(sysConfig);
    return AjaxResponse.success("更新配置成功！");
  }

  @PostMapping(value = "/add")
  public AjaxResponse add(@RequestBody SysConfig sysConfig) {
    sysconfigService.addConfig(sysConfig);
    return AjaxResponse.success("新增配置成功！");
  }

  @PostMapping(value = "/delete")
  public AjaxResponse delete(@RequestParam Long configId) {
    sysconfigService.deleteConfig(configId);
    return AjaxResponse.success("删除配置成功!");
  }

}
