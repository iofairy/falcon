package com.iofairy.test.node;

import com.iofairy.falcon.node.Hierarchy;
import lombok.Data;

@Data
public class DeptHierarchy extends Hierarchy<DeptHierarchy, Integer> {
    private String deptName;
    private String email;

    public DeptHierarchy() {
    }

    public DeptHierarchy(String tenantId, Integer parentId, Integer nodeId, String deptName, String email) {
        super(tenantId, parentId, nodeId);
        this.deptName = deptName;
        this.email = email;
    }


    @Override
    public String toString() {
        return "Dept{" +
                "deptName='" + deptName + '\'' +
                ", email=" + email +
                ", tenantId='" + tenantId + '\'' +
                ", parentId=" + parentId +
                ", nodeId=" + nodeId +
                ", depth=" + depth +
                ", order=" + order +
                ", children=" + children +
                '}';
    }


}
