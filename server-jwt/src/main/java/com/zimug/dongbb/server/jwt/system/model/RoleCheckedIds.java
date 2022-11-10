package com.zimug.dongbb.server.jwt.system.model;

import java.util.List;

public class RoleCheckedIds {

  private String roleCode;

  private Long roleId;

  private List<Long> checkedIds;

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public List<Long> getCheckedIds() {
    return checkedIds;
  }

  public void setCheckedIds(List<Long> checkedIds) {
    this.checkedIds = checkedIds;
  }
}
