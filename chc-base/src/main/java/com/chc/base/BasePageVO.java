package com.chc.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 分页基础类
 *
 * @author cuihaochong
 * @date 2020/3/9
 */
@Data
public class BasePageVO implements Serializable {

    private static final long serialVersionUID = 3388730977051646439L;

    /**
     * 当前页
     */
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JSONField(serialize = false)
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JSONField(serialize = false)
    private Integer pageSize = 10;
}
