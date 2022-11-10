package com.zimug.dongbb.persistence.system.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zimug.dongbb.persistence.system.model.SysApi;
import com.zimug.dongbb.persistence.system.model.SysMenu;
import com.zimug.dongbb.persistence.system.model.SysOrg;
import com.zimug.dongbb.persistence.system.model.SysUserOrg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MySystemMapper {

  List<SysOrg> selectOrgTree(@Param("rootOrgId") Long rootOrgId,
                             @Param("orgNameLike") String orgNameLike,
                             @Param("orgStatus") Boolean orgStatus);

  List<SysMenu> selectMenuTree(@Param("rootMenuId") Long rootMenuId,
                               @Param("menuNameLike") String menuNameLike,
                               @Param("menuStatus") Boolean menuStatus);

  List<SysApi> selectApiTree(@Param("rootApiId") Long rootApiId,
                             @Param("apiNameLike") String apiNameLike,
                             @Param("apiStatus") Boolean apiStatus);


  Integer insertRoleMenuIds(@Param("roleId") Long roleId,
                            @Param("checkedIds") List<Long> checkedIds);

  Integer insertRoleApiIds(@Param("roleId") Long roleId,
                           @Param("checkedIds") List<Long> checkedIds);

  List<String> selectApiExpandedKeys();

  List<String> selectMenuExpandedKeys();

  List<String> selectApiCheckedKeys(Long roleId);

  List<String> selectMenuCheckedKeys(Long roleId);

  IPage<SysUserOrg> selectUser(Page<SysUserOrg> page,
                               @Param("orgId") Long orgId,
                               @Param("username") String username,
                               @Param("phone") String phone,
                               @Param("email") String email,
                               @Param("enabled") Boolean enabled,
                               @Param("createStartTime") Date createStartTime,
                               @Param("createEndTime") Date createEndTime);

  List<String> getCheckedRoleIds(Long userId);

  Long insertUserRoleIds(@Param("userId") Long userId,
                            @Param("checkedIds") List<Long> checkedIds);

  List<SysMenu> selectMenuByUsername(@Param("username") String username);
}
