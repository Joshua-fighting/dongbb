package com.zimug.dongbb.persistence.system.model;

import lombok.Data;

/**
 * sys_role_menu
 * @author
 */
@Data
public class SysRoleMenu  {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long menuId;


}