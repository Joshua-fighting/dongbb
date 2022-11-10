package com.zimug.dongbb.server.jwt.system.model;

import com.zimug.commons.model.tree.DataTree;
import com.zimug.dongbb.persistence.system.model.SysOrg;


import java.util.List;

public class SysOrgNode extends SysOrg implements DataTree<SysOrgNode,Long> {

    private List<SysOrgNode> children;

    @Override
    public Long getParentId() {
        return super.getOrgPid();
    }

    @Override
    public void setChildren(List<SysOrgNode> children) {
        this.children = children;
    }

    @Override
    public List<SysOrgNode> getChildren() {
        return this.children;
    }
}
