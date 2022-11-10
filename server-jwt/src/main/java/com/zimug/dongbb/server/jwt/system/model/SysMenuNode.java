package com.zimug.dongbb.server.jwt.system.model;

import com.zimug.commons.model.tree.DataTree;
import com.zimug.dongbb.persistence.system.model.SysMenu;


import java.util.ArrayList;
import java.util.List;

public class SysMenuNode extends SysMenu implements DataTree<SysMenuNode,Long> {

    private List<SysMenuNode> children = new ArrayList<>();

    private String path;
    private String name;

    public String getPath() {
      return this.getUrl();
    }

    public String getName() {
      return this.getMenuName();
    }

    @Override
    public Long getParentId() {
        return super.getMenuPid();
    }

    @Override
    public void setChildren(List<SysMenuNode> children) {
        this.children = children;
    }

    @Override
    public List<SysMenuNode> getChildren() {
        return this.children;
    }
}
