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
 * 层级树
 *
 * @param <T> 层级树类型
 * @param <E> 节点ID类型
 * @since 0.5.0
 */
@Beta
public class Hierarchy<T extends Hierarchy<T, E>, E> implements Serializable {
    private static final long serialVersionUID = 99856752265833656L;

    /**
     * 租户ID
     */
    protected String tenantId;
    /**
     * 父节点ID
     */
    protected E parentId;
    /**
     * 祖先节点ID链（所有祖先节点ID列表）
     */
    protected List<E> ancestors;
    /**
     * 本节点ID
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
