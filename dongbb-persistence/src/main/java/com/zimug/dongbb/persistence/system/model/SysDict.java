package com.zimug.dongbb.persistence.system.model;

import com.zimug.dongbb.persistence.system.common.BaseColumns;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseColumns {

    //
    private Long id;
    //分组名称
    private String groupName;
    //分组编码
    private String groupCode;
    //字典项名称
    private String itemName;
    //字典项Value
    private String itemValue;
    //字典项描述
    private String itemDesc;

}