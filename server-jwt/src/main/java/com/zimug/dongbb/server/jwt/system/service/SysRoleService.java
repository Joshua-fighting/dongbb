package com.zimug.dongbb.server.jwt.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zimug.dongbb.persistence.system.mapper.MySystemMapper;
import com.zimug.dongbb.persistence.system.mapper.SysRoleMapper;
import com.zimug.dongbb.persistence.system.mapper.SysUserRoleMapper;
import com.zimug.dongbb.persistence.system.model.SysRole;
import com.zimug.dongbb.persistence.system.model.SysUser;
import com.zimug.dongbb.persistence.system.model.SysUserRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zimug.commons.cachekey.RBACCacheKey.ROLE_CODES;

@Service
public class SysRoleService {

  @Resource
  private SysRoleMapper sysRoleMapper;
  @Resource
  private MySystemMapper mySystemMapper;
  @Resource
  private SysUserRoleMapper sysUserRoleMapper;

  /**
   * 根据参数查询角色记录
   * @param roleLik 角色编码 或角色描述 或角色名称模糊查询
   * @return 角色记录列表
   */
  public List<SysRole> queryRoles(String roleLik) {
    QueryWrapper<SysRole> query = new QueryWrapper<>();
    query.like(StringUtils.isNotEmpty(roleLik),"role_code",roleLik)
      .or()
      .like(StringUtils.isNotEmpty(roleLik),"role_desc",roleLik)
      .or()
      .like(StringUtils.isNotEmpty(roleLik),"role_name",roleLik);
    query.orderByAsc("role_sort");

    return sysRoleMapper.selectList(query);
  }

  //角色管理：更新角色信息
  @CacheEvict(value = ROLE_CODES,allEntries = true)
  public void updateRole(SysRole sysrole){
    Assert.isTrue(sysrole.getId() != null,
      "更新数据必须指定数据更新条件（主键）");
    sysRoleMapper.updateById(sysrole);
  }

  //角色管理：删除角色
  @CacheEvict(value = ROLE_CODES,allEntries = true)
  public void deleteRole(Long id){
    Assert.isTrue(id != null,
      "删除数据必须指定数据删除条件（主键）");
    sysRoleMapper.deleteById(id);
  }

  //角色管理：更新角色的禁用状态
  @CacheEvict(value = ROLE_CODES,allEntries = true)
  public void updateStatus(Long id,Boolean status){
    Assert.isTrue(id != null, "修改操作必须带主键");
    SysRole sysRole = new SysRole();
    sysRole.setId(id);
    sysRole.setStatus(status);
    sysRoleMapper.updateById(sysRole);
  }

  public void addRole(SysRole sysrole){
    sysrole.setStatus(false); //是否禁用:false
    sysRoleMapper.insert(sysrole);
  }

  //获取角色记录及某用户勾选角色记录
  public Map<String,Object> getRolesAndChecked(Long userId){
    Assert.isTrue(userId != null,
      "获取角色信息必须传入用户id作为参数");

    Map<String,Object> ret = new HashMap<>();
    //所有角色记录
    ret.put("roleDatas",sysRoleMapper.selectList(null));
    //某用户具有的角色id列表
    ret.put("checkedRoleIds",mySystemMapper.getCheckedRoleIds(userId));
    return ret;
  }

  //保存某用户勾选的角色id数据,为用户重新分配角色
  @Transactional
  @CacheEvict(value = ROLE_CODES,key = "#username")
  public void saveCheckedKeys(String username, Long userId,List<Long> checkedIds){
    sysUserRoleMapper.delete(new UpdateWrapper<SysUserRole>()
                            .eq("user_id",userId));
    mySystemMapper.insertUserRoleIds(userId,checkedIds);
  }

}
