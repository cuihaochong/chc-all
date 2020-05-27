package com.chc.api;

/**
 * Description: 账户Api
 *
 * @author cuihaochong
 * @date 2019/12/12
 */
public interface AccountApi {

    /**
     * 转账
     *
     * @param money 金额
     */
    void transfer(int money);
}
