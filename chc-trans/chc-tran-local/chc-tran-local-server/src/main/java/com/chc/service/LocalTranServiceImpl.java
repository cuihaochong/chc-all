package com.chc.service;

import com.chc.api.AccountApi;
import com.chc.mapper.BankAMapper;
import com.chc.mapper.BankBMapper;
import com.chc.pojo.entity.BankA;
import com.chc.pojo.entity.BankB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Service("localTranService")
public class LocalTranServiceImpl implements AccountApi {
    @Autowired
    BankAMapper bankAMapper;
    @Autowired
    BankBMapper bankBMapper;

    @Override
    public void transfer(int money) {
        // a转账给b
        bankAMapper.insert(BankA.initial(-money));
        System.out.println("BankA转账: " + -money);
        bankBMapper.insert(BankB.initial(money));
        System.out.println("BankB收账: " + money);

        if (money > 20) {
            throw new RuntimeException("money too large");
        }
    }
}
