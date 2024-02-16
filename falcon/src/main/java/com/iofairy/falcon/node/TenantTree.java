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
 * 带租户的层级树
 *
 * @param <T> 层级树
 * @since 0.5.0
 */
@Beta
public class TenantTree<T extends Hierarchy<T, ?>> implements Serializable {
    private static final long serialVersionUID = 99856752265833660L;

    /**
     * 租户ID
     */
    String tenantId;
    /**
     * 子节点
     */
    List<T> topNodes;

    public TenantTree() {
    }

    public TenantTree(String tenantId, List<T> topNodes) {
        this.tenantId = tenantId;
        this.topNodes = topNodes;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<T> getTopNodes() {
        return topNodes;
    }

    public void setTopNodes(List<T> topNodes) {
        this.topNodes = topNodes;
    }

    @Override
    public String toString() {
        return "TenantTree{" +
                "tenantId='" + tenantId + '\'' +
                ", topNodes=" + topNodes +
                '}';
    }
}
