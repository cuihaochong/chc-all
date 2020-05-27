package com.chc.util.tree;

import java.util.List;

/**
 * Description: 树结构基础类
 *
 * @author cuihaochong
 * @date 2019/9/24
 */
public interface TreeEntity<E> {

    /**
     * 获取当前节点id
     *
     * @return 当前节点id
     */
    String getNodeId();

    /**
     * 获取父节点id
     *
     * @return 父节点id
     */
    String getNodePid();

    /**
     * 在当前节点插入子节点集合
     *
     * @param childList 子节点集合
     */
    void setChildList(List<E> childList);

}
