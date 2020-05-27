package com.chc.tree;

import com.alibaba.fastjson.JSON;
import com.chc.util.tree.TreeParser;
import com.chc.util.tree.eg.OrgDemo;
import org.junit.Test;

import java.util.List;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/26
 */
public class TreeParserTest {

    @Test
    public void getTreeList() {
        List<OrgDemo> init = OrgDemo.getInit();
        init.forEach(o -> {
            o.setNodeId(o.getId() + "");
            o.setNodePid(o.getParentId() + "");
        });
        List<OrgDemo> treeList = TreeParser.getTreeList("0", init);
        System.out.println(JSON.toJSONString(treeList));
    }
}