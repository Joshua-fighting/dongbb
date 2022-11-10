package com.zimug.dongbb.server.jwt.system.model;

import com.zimug.commons.model.tree.DataTree;
import com.zimug.dongbb.persistence.system.model.SysApi;


import java.util.List;

public class SysApiNode extends SysApi implements DataTree<SysApiNode,Long> {

    private List<SysApiNode> children;

    @Override
    public Long getParentId() {
        return super.getApiPid();
    }

    @Override
    public void setChildren(List<SysApiNode> children) {
        this.children = children;
    }

    @Override
    public List<SysApiNode> getChildren() {
        return this.children;
    }
}
