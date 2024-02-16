package com.iofairy.test.node;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iofairy.falcon.node.Hierarchy;
import com.iofairy.top.G;
import lombok.Data;

import java.util.Date;

/**
 * @author GG
 * @version 1.0
 * @date 2024/1/20 11:56
 */
@Data
public class Dept extends Hierarchy<Dept, Integer> {
    private String deptName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Dept() {
    }

    public Dept(String tenantId, Integer parentId, Integer nodeId, String deptName, Date createTime) {
        super(tenantId, parentId, nodeId);
        this.deptName = deptName;
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Dept{" +
                "deptName='" + deptName + '\'' +
                ", createTime=" + G.toString(createTime) +
                ", tenantId='" + tenantId + '\'' +
                ", parentId=" + parentId +
                ", nodeId=" + nodeId +
                ", depth=" + depth +
                ", order=" + order +
                ", children=" + children +
                '}';
    }

}
