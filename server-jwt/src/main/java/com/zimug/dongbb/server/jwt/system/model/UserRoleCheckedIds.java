package com.zimug.dongbb.server.jwt.system.model;

import java.util.List;

public class UserRoleCheckedIds {

  private String username;

  private Long userId;

  private List<Long> checkedIds;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public List<Long> getCheckedIds() {
    return checkedIds;
  }

  public void setCheckedIds(List<Long> checkedIds) {
    this.checkedIds = checkedIds;
  }
}
