package com.zimug.dongbb.persistence.system.model;

import com.zimug.dongbb.persistence.system.common.BaseColumns;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sys_org
 * @author 字母哥
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOrg extends BaseColumns {
    private Long id;

    /**
     * 上级组织编码
     */
    private Long orgPid;

    /**
     * 所有的父节点id
     */
    private String orgPids;

    /**
     * 0:不是叶子节点，1:是叶子节点
     */
    private Boolean isLeaf;

    /**
     * 组织名
     */
    private String orgName;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 排序
     */
    private Integer orgSort;

    /**
     * 组织层级
     */
    private Integer level;

    /**
     * 0:启用,1:禁用
     */
    private Boolean status;

}