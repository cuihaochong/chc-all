package com.chc.util.tree.eg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.chc.util.tree.TreeEntity;
import com.chc.util.tree.TreeParser;
import lombok.Data;

import java.util.*;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/24
 */
@Data
public class OrgDemo implements TreeEntity<OrgDemo> {


    private Integer id;
    private Integer parentId;

    @JSONField(serialize = false)
    private String nodeId;
    @JSONField(serialize = false)
    private String nodePid;
    private List<OrgDemo> childList;


    public OrgDemo(Integer id, Integer parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public static List<OrgDemo> getInit() {
        return Arrays.asList(new OrgDemo(1, 0),
            new OrgDemo(3, 1), new OrgDemo(2, 1),
            new OrgDemo(5, 3), new OrgDemo(4, 3));
    }

    public static void main(String[] args) {
        List<OrgDemo> init = OrgDemo.getInit();
        init.forEach(o -> {
            o.setNodeId(o.getId() + "");
            o.setNodePid(o.getParentId() + "");
        });
        List<OrgDemo> treeList = TreeParser.getTreeList("0", init, true, Comparator.comparing(OrgDemo::getId));
        System.out.println(JSON.toJSONString(treeList));
    }
}
