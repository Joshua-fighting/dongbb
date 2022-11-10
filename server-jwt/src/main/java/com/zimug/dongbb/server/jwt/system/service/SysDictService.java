package com.zimug.dongbb.server.jwt.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zimug.dongbb.persistence.system.mapper.SysDictMapper;
import com.zimug.dongbb.persistence.system.model.SysDict;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 数据字典表服务层代码
 * @author 字母哥
 */
@Service
public class SysDictService {

  @Resource
  private SysDictMapper sysDictMapper;

  /**
   * 查询所有
   */
  public List<SysDict> all(){
    return  sysDictMapper.selectList(null);
  }

  /**
   * 根据参数查询
   * @param groupName 分组名称
   * @param groupCode 分组编码
   */
  public List<SysDict> query(
        String groupName,
        String groupCode ) {
    LambdaQueryWrapper<SysDict> lambdaQ = Wrappers.lambdaQuery();
    lambdaQ
        .like(StringUtils.isNotEmpty(groupName),SysDict::getGroupName,groupName)
        .like(StringUtils.isNotEmpty(groupCode),SysDict::getGroupCode,groupCode);

    return sysDictMapper.selectList(lambdaQ);
  }

  /**
   * 根据id更新数据字典项
   * @param sysDict 更新实体(包含id)
   */
  public void update(SysDict sysDict){
    Assert.isTrue(sysDict.getId() != null,
      "更新数据必须指定数据更新条件（主键）");

    sysDictMapper.updateById(sysDict);
  }

  /**
   * 新增数据字典项
   * @param sysDict 新增实体
   */
  public void add(SysDict sysDict){

    sysDictMapper.insert(sysDict);
  }

  /**
   * 根据id删除数据字典项
   * @param id  删除项的id
   */
  public void delete(Long id){
    Assert.isTrue(id != null,
      "删除数据必须指定数据删除条件（主键）");
    sysDictMapper.deleteById(id);
  }

}
