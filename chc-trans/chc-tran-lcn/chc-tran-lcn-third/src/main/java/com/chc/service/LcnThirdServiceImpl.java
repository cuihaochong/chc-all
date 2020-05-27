package com.chc.service;

import com.chc.api.AccountApi;
import com.chc.mapper.BankBMapper;
import com.chc.pojo.entity.BankB;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Service("lcnThirdService")
public class LcnThirdServiceImpl implements AccountApi {
    @Autowired
    BankBMapper bankBMapper;

    @Override
    @LcnTransaction(propagation = DTXPropagation.SUPPORTS)
    public void transfer(int money) {

        bankBMapper.insert(BankB.initial(money));
        System.out.println("BankB收账: " + money);
        System.out.println(TracingContext.tracing().groupId());
        if (money >= 30) {
            throw new RuntimeException("third server is error : money too large");
        }
    }
}
