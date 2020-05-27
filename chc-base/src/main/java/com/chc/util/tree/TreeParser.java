package com.chc.util.tree;

import com.chc.util.str.StringUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/24
 */
public class TreeParser {

    /**
     * 解析树形数据
     * <p>包含top节点<p/>
     *
     * @param topId      顶层节点id
     * @param entityList 节点数据集合
     * @return 树形结构数据
     */
    public static <E extends TreeEntity<E>> List<E> getTreeList(String topId, List<E> entityList) {
        return getTreeList(topId, entityList, true);
    }

    /**
     * 解析树形数据
     *
     * @param topId      顶层节点id
     * @param entityList 节点数据集合
     * @param haveTop    是否包含top节点
     * @return 树形结构数据
     */
    public static <E extends TreeEntity<E>> List<E> getTreeList(String topId, List<E> entityList, boolean haveTop) {
        return getTreeList(topId, entityList, haveTop, null);
    }

    /**
     * 解析树形数据
     * <p>包含top节点<p/>
     *
     * @param topId      顶层节点id
     * @param entityList 节点数据集合
     * @param c          自定义比较器
     * @return 树形结构数据
     */
    public static <E extends TreeEntity<E>> List<E> getTreeList(String topId, List<E> entityList, Comparator<? super E> c) {
        return getTreeList(topId, entityList, true, c);
    }

    /**
     * 解析树形数据
     *
     * @param topId      顶层节点id
     * @param entityList 节点数据集合
     * @param haveTop    是否包含top节点
     * @param c          自定义比较器
     * @return 树形结构数据
     */
    public static <E extends TreeEntity<E>> List<E> getTreeList(String topId, List<E> entityList
        , boolean haveTop, Comparator<? super E> c) {
        List<E> resultList = new ArrayList<>();
        // 获取顶层元素集合
        if (StringUtil.isBlank(topId)) {
            // 传入topId为null或者为0,不处理haveTop
            entityList.stream()
                .filter(entity -> StringUtil.isBlank(entity.getNodePid()))
                .forEach(resultList::add);
        } else if (StringUtil.equals("0", topId) || StringUtil.equals("null", topId)) {
            entityList.stream()
                .filter(entity -> topId.equals(entity.getNodePid()))
                .forEach(resultList::add);
        } else {
            if (haveTop) {
                // 包含顶层节点自身
                entityList.stream()
                    .filter(entity -> topId.equals(entity.getNodeId()))
                    .forEach(resultList::add);
            } else {
                entityList.stream()
                    .filter(entity -> topId.equals(entity.getNodePid()))
                    .forEach(resultList::add);
            }
        }
        if (CollectionUtils.isEmpty(resultList)) {
            return resultList;
        }
        if (c != null) {
            resultList.sort(c);
        }
        // 获取每个顶层元素的子数据集合
        resultList.forEach(entity -> entity.setChildList(getSubList(entity.getNodeId(), entityList, c)));
        return resultList;
    }

    /**
     * * 获取子数据集合
     *
     * @param nodePid    节点pid
     * @param entityList 节点数据集合
     * @return 子数据集合
     */
    private static <E extends TreeEntity<E>> List<E> getSubList(String nodePid, List<E> entityList) {
        return getSubList(nodePid, entityList, null);
    }

    /**
     * * 获取子数据集合
     *
     * @param nodePid    节点pid
     * @param entityList 节点数据集合
     * @param c          自定义比较器
     * @return 子数据集合
     */
    private static <E extends TreeEntity<E>> List<E> getSubList(String nodePid, List<E> entityList
        , Comparator<? super E> c) {
        List<E> childList = new ArrayList<>();
        // 子集的直接子对象
        entityList.stream()
            .filter(entity -> nodePid.equals(entity.getNodePid()))
            .forEach(childList::add);
        // 排序
        if (c != null) {
            childList.sort(c);
        }
        // 子集的间接子对象,递归调用
        childList.forEach(entity -> entity.setChildList(getSubList(entity.getNodeId(), entityList, c)));
        // 递归退出条件
        return childList;
    }


}
