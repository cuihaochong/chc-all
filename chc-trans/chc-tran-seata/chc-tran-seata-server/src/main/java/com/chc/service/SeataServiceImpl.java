package com.chc.service;

import com.chc.api.AccountApi;
import com.chc.client.SeataThirdClient;
import com.chc.mapper.BankAMapper;
import com.chc.pojo.entity.BankA;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Service("seataService")
public class SeataServiceImpl implements AccountApi {

    @Autowired
    BankAMapper bankAMapper;
    @Autowired
    SeataThirdClient seataThirdClient;

    @Override
    @GlobalTransactional
    public void transfer(int money) {
        bankAMapper.insert(BankA.initial(-money));
        System.out.println("BankA转账: " + -money);

        seataThirdClient.transfer(money);
        System.out.println("第三方BankA转账: " + -money);

        if (money >= 20 && money < 30) {
            throw new RuntimeException("server is error,money too large");
        }
    }
}
