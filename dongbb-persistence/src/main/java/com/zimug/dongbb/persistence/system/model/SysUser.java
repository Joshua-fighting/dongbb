package com.zimug.dongbb.persistence.system.model;

import com.zimug.dongbb.persistence.system.common.BaseColumns;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * sys_user表对应的实体类
 * @author 字母哥
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseColumns {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户所属组织id
     */
    private Long orgId;

    /**
     * 0无效用户，1是有效用户
     */
    private Boolean enabled;

    /**
     * 手机号
     */
    private String phone;

    /**
     * email
     */
    private String email;



}