package com.zimug.dongbb.persistence.system.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserOrg extends SysUser {

  private String orgName;


}
