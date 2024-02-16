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
import com.iofairy.falcon.stream.CollectorKit;
import com.iofairy.top.G;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 层级树构造器
 *
 * @param <T> 节点类型
 * @param <E> 节点ID类型
 * @since 0.5.0
 */
@Beta
public class HierarchyBuilder<T extends Hierarchy<T, E>, E> {
    private final List<T> nodes;

    private HierarchyBuilder(List<T> nodes) {
        this.nodes = G.isEmpty(nodes) ? new ArrayList<>() : nodes;
    }

    public static <T extends Hierarchy<T, E>, E> HierarchyBuilder<T, E> of(List<T> nodes) {
        return new HierarchyBuilder<>(nodes);
    }

    public static <T extends Hierarchy<T, E>, E> HierarchyBuilder<T, E> on(List<T> nodes) {
        return new HierarchyBuilder<>(nodes);
    }

    public List<T> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    /*========================
     ********* 构建树 *********
     *========================
     */

    /**
     * 构建树
     *
     * @param topParents 顶层部门父ID的值
     * @param fillDepth  是否填充树深度
     * @param fillOrder  是否填充节点在当前深度的序号
     * @param c          比较器
     * @return 返回层级树
     */
    public List<T> buildTree(List<E> topParents, boolean fillDepth, boolean fillOrder, Comparator<? super T> c) {
        if (G.isEmpty(nodes)) return new ArrayList<>();
        if (G.isEmpty(topParents)) throw new NullPointerException("参数`topParents`不能为空！");

        return createTree(nodes, topParents, fillDepth, fillOrder, c);
    }

    /**
     * 构建树
     *
     * @param topParents 顶层部门父ID的值
     * @param fillDepth  是否填充树深度
     * @return 返回层级树
     */
    public List<T> buildTree(List<E> topParents, boolean fillDepth) {
        return buildTree(topParents, fillDepth, false, null);
    }

    /**
     * 构建树
     *
     * @param topParents 顶层部门父ID的值
     * @return 返回层级树
     */
    public List<T> buildTree(List<E> topParents) {
        return buildTree(topParents, false, false, null);
    }

    /**
     * 构建树（按租户区分）
     *
     * @param topParents 顶层部门父ID的值
     * @param fillDepth  是否填充树深度
     * @param fillOrder  是否填充节点在当前深度的序号
     * @param c          比较器
     * @return 返回层级树
     */
    public List<TenantTree<T>> buildTenantTree(List<E> topParents, boolean fillDepth, boolean fillOrder, Comparator<? super T> c) {
        if (G.isEmpty(nodes)) return new ArrayList<>();
        if (G.isEmpty(topParents)) throw new NullPointerException("参数`topParents`不能为空！");
        Map<String, List<T>> tenantMap = nodes.parallelStream().collect(CollectorKit.groupingByAllowNullKey(h -> h.tenantId));
        List<TenantTree<T>> tenantList = new ArrayList<>();
        tenantMap.forEach((k, vs) -> {
            List<T> parentList = createTree(vs, topParents, fillDepth, fillOrder, c);
            tenantList.add(new TenantTree<>(k, parentList));
        });
        return tenantList;
    }

    /**
     * 构建树（按租户区分）
     *
     * @param topParents 顶层部门父ID的值
     * @param fillDepth  是否填充树深度
     * @return 返回层级树
     */
    public List<TenantTree<T>> buildTenantTree(List<E> topParents, boolean fillDepth) {
        return buildTenantTree(topParents, fillDepth, false, null);
    }

    /**
     * 构建树（按租户区分）
     *
     * @param topParents 顶层部门父ID的值
     * @return 返回层级树
     */
    public List<TenantTree<T>> buildTenantTree(List<E> topParents) {
        return buildTenantTree(topParents, false, false, null);
    }

    private List<T> createTree(List<T> nodeList, List<E> topParents, boolean fillDepth, boolean fillOrder, Comparator<? super T> c) {
        Map<E, List<T>> parentMap = nodeList.parallelStream().collect(CollectorKit.groupingByAllowNullKey(h -> h.parentId));
        parentMap.forEach((k, vs) -> {
            /*排序*/
            if (c != null) vs.sort(c);
            /*填充序号*/
            if (fillOrder) {
                for (int i = 0; i < vs.size(); i++) {
                    vs.get(i).order = i;
                }
            }
        });

        /*设置子节点*/
        nodeList.forEach(node -> node.children = (parentMap.get(node.nodeId)));
        /*获取最顶层的节点*/
        List<T> parentList = nodeList.stream().filter(node -> topParents.contains(node.parentId)).collect(Collectors.toList());
        /*顶层节点排序*/
        if (c != null) parentList.sort(c);
        /*顶层节点填充序号*/
        if (fillOrder) {
            for (int i = 0; i < parentList.size(); i++) {
                parentList.get(i).order = i;
            }
        }
        /*填充树深度*/
        if (fillDepth) fillDepth(parentList, 0);

        return parentList;
    }

    private void fillDepth(List<T> list, int depth) {
        for (T t : list) {
            t.depth = depth;
            if (!G.isEmpty(t.children)) {
                fillDepth(t.children, depth + 1);
            }
        }
    }

}
