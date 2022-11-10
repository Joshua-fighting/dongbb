package com.zimug.dongbb.persistence.system.model;

import com.zimug.dongbb.persistence.system.common.BaseColumns;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sys_menu
 * @author 字母哥
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseColumns {
    private Long id;

    /**
     * 父菜单ID
     */
    private Long menuPid;

    /**
     * 当前菜单所有父菜单
     */
    private String menuPids;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 排序
     */
    private Integer menuSort;


    /**
     * 0:不是叶子节点，1:是叶子节点
     */
    private Boolean isLeaf;

    /**
     * 跳转URL
     */
    private String url;

    private String icon;

    /**
     * 菜单层级
     */
    private Integer level;

    /**
     * 0:启用,1:禁用
     */
    private Boolean status;


    /**
     * 0:不隐藏,1:隐藏（是否隐藏菜单，某些页面入口不在菜单上显示）
     */
    private Boolean hidden;


    /**
     * 前端路由组件页面文件import路径
     */
    private String viewImport;


}