package com.chc.service;

import com.chc.api.AccountApi;
import com.chc.mapper.BankBMapper;
import com.chc.pojo.entity.BankB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Service("seataThirdService")
public class SeataThirdServiceImpl implements AccountApi {
    @Autowired
    BankBMapper bankBMapper;

    @Override
    public void transfer(int money) {

        bankBMapper.insert(BankB.initial(money));
        System.out.println("BankB收账: " + money);

        if (money >= 30) {
            throw new RuntimeException("third server is error : money too large");
        }
    }
}
