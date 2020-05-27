package com.chc.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

/**
 * Description:
 *
 * @author cuihaochong
 * @dae 2019/12/13
 */
@TableName("bank_b")
public class BankB implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 741571546258891949L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * userName
     */
    private String userName;

    /**
     * money
     */
    private Long money;

    /**
     * status
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BankB(Integer id, String userName, Long money, String status) {
        this.id = id;
        this.userName = userName;
        this.money = money;
        this.status = status;
    }

    public static BankB initial(long money) {
        return new BankB(null, "BankB", money, null);
    }
}
