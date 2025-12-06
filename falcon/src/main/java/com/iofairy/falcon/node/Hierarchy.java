/*
 * Copyright (C) 2021 iofairy, <https://github.com/iofairy/falcon>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iofairy.falcon.node;

import com.iofairy.annos.Beta;

import java.io.Serializable;
import java.util.List;

/**
 * 层级树 <br><br>
 *
 * <b>使用样例:</b>
 * <blockquote><pre>{@code
 * // 1、继承 Hierarchy 类
 * import com.iofairy.falcon.node.Hierarchy;
 * import lombok.Data;
 *
 * @Data
 * public class DeptHierarchy extends Hierarchy<DeptHierarchy, Integer> {
 *     private String deptName;
 *     private String email;
 *
 *     public DeptHierarchy() {
 *     }
 *
 *     public DeptHierarchy(String tenantId, Integer parentId, Integer nodeId, String deptName, String email) {
 *         super(tenantId, parentId, nodeId);
 *         this.deptName = deptName;
 *         this.email = email;
 *     }
 * }
 *
 * // 2、构建 DeptHierarchy 列表，创建树结构
 * import com.fasterxml.jackson.databind.ObjectMapper;
 * import com.iofairy.falcon.node.HierarchyBuilder;
 * import com.iofairy.falcon.node.TenantTree;
 * import lombok.SneakyThrows;
 * import org.junit.jupiter.api.Test;
 *
 * public class HierarchyTest {
 *
 *     static ObjectMapper mapper = new ObjectMapper();
 *
 *     @SneakyThrows
 *     @Test
 *     public void testBuildTree() {
 *         DeptHierarchy dept1 = new DeptHierarchy(null, 0, 1, "部门1", "email1@aa.com");
 *         DeptHierarchy dept2 = new DeptHierarchy(null, null, 2, "部门2", "email2@aa.com");
 *         DeptHierarchy dept10 = new DeptHierarchy(null, 1, 10, "部门10", "email10@aa.com");
 *         DeptHierarchy dept11 = new DeptHierarchy(null, 1, 11, "部门11", "email11@aa.com");
 *         DeptHierarchy dept12 = new DeptHierarchy(null, 1, 12, "部门12", "email12@aa.com");
 *         DeptHierarchy dept13 = new DeptHierarchy(null, 2, 13, "部门13", "email13@aa.com");
 *         DeptHierarchy dept112 = new DeptHierarchy(null, 12, 112, "部门112", "email112@aa.com");
 *         DeptHierarchy dept1110 = new DeptHierarchy(null, 112, 1110, "部门1110", "email1110@aa.com");
 *         DeptHierarchy dept3 = new DeptHierarchy("tenant1", null, 3, "部门3", "email3@aa.com");
 *         DeptHierarchy dept4 = new DeptHierarchy("tenant1", 0, 4, "部门4", "email4@aa.com");
 *         DeptHierarchy dept15 = new DeptHierarchy("tenant1", 3, 15, "部门15", "email15@aa.com");
 *         DeptHierarchy dept16 = new DeptHierarchy("tenant1", 3, 16, "部门16", "email16@aa.com");
 *         DeptHierarchy dept116 = new DeptHierarchy("tenant1", 16, 116, "部门116", "email116@aa.com");
 *
 *         List<DeptHierarchy> depts = Arrays.asList(dept1, dept2, dept3, dept4, dept10, dept11, dept12, dept13, dept15, dept16, dept112, dept116, dept1110);
 *         HierarchyBuilder<DeptHierarchy, Integer> builder = HierarchyBuilder.on(depts);
 *
 *          // 创建普通的部门树结构
 *         List<DeptHierarchy> deptTree = builder.buildTree(Arrays.asList(0, null), true, true, true, null);
 *         String tree = mapper.writeValueAsString(deptTree);
 *         System.out.println(tree);
 *         System.out.println("============================================================");
 *         // 创建带租户的部门树结构
 *         List<TenantTree<DeptHierarchy>> tenantTrees = builder.buildTenantTree(Arrays.asList(0, null), true, true, true, null);
 *         String tenantTree = mapper.writeValueAsString(tenantTrees);
 *         System.out.println(tenantTree);
 *     }
 * }
 *
 * }</pre></blockquote>
 *
 * @param <T> 层级树类型
 * @param <E> 节点ID类型
 * @since 0.5.0
 */
@Beta
public class Hierarchy<T extends Hierarchy<T, E>, E> implements Serializable {
    private static final long serialVersionUID = 99856752265833656L;

    /**
     * 租户ID。<b>（按需设置）</b>
     */
    protected String tenantId;
    /**
     * 父节点ID。<b>（需填充此字段）</b>
     */
    protected E parentId;
    /**
     * 祖先节点ID链（所有祖先节点ID列表）
     */
    protected List<E> ancestors;
    /**
     * 本节点ID。<b>（需填充此字段）</b>
     */
    protected E nodeId;
    /**
     * 树深度
     */
    protected int depth;
    /**
     * 在当前层级的顺序
     */
    protected int order;
    /**
     * 子节点
     */
    protected List<T> children;

    public Hierarchy() {
    }

    public Hierarchy(String tenantId, E parentId, E nodeId) {
        this.tenantId = tenantId;
        this.parentId = parentId;
        this.nodeId = nodeId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public E getParentId() {
        return parentId;
    }

    public void setParentId(E parentId) {
        this.parentId = parentId;
    }

    public E getNodeId() {
        return nodeId;
    }

    public void setNodeId(E nodeId) {
        this.nodeId = nodeId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public List<E> getAncestors() {
        return ancestors;
    }

    public void setAncestors(List<E> ancestors) {
        this.ancestors = ancestors;
    }

    @Override
    public String toString() {
        return "Hierarchy{" +
                "tenantId='" + tenantId + '\'' +
                ", parentId=" + parentId +
                ", ancestors=" + ancestors +
                ", nodeId=" + nodeId +
                ", depth=" + depth +
                ", order=" + order +
                ", children=" + children +
                '}';
    }
}
