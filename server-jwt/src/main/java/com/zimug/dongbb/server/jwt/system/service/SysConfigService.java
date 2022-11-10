package com.zimug.dongbb.server.jwt.system.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zimug.commons.exception.CustomException;
import com.zimug.commons.exception.CustomExceptionType;
import com.zimug.dongbb.persistence.system.mapper.SysConfigMapper;
import com.zimug.dongbb.persistence.system.model.SysConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysConfigService {

  @Resource
  private SysConfigMapper sysConfigMapper;

  public List<SysConfig> queryConfigs(String configLik) {
    QueryWrapper<SysConfig> query = new QueryWrapper<>();
    query.like(StringUtils.isNotEmpty(configLik),"param_name",configLik)
         .or()
         .like(StringUtils.isNotEmpty(configLik),"param_key",configLik);

    return sysConfigMapper.selectList(query);
  }

  public void updateConfig(SysConfig sysconfig){
    if(sysconfig.getId() == null){
      throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
        "修改操作必须带主键");
    }else{
      sysConfigMapper.updateById(sysconfig);
    }
  }

  public void addConfig(SysConfig sysconfig){
    sysConfigMapper.insert(sysconfig);
  }

  public void deleteConfig(Long configId){
    sysConfigMapper.deleteById(configId);
  }

}
