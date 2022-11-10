package com.zimug.dongbb.persistence.system.model;

import com.zimug.dongbb.persistence.system.common.BaseColumns;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * sys_config
 * @author 字母哥
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseColumns {
    private Long id;

    /**
     * 参数名称(中文)
     */
    private String paramName;

    /**
     * 参数唯一标识(英文及数字)
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 参数描述备注
     */
    private String paramDesc;



}