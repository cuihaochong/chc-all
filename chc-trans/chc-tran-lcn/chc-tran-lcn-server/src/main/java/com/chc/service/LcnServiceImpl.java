package com.chc.service;

import com.chc.api.AccountApi;
import com.chc.client.LcnThirdClient;
import com.chc.mapper.BankAMapper;
import com.chc.pojo.entity.BankA;
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
@Service("lcnService")
public class LcnServiceImpl implements AccountApi {

    @Autowired
    BankAMapper bankAMapper;
    @Autowired
    LcnThirdClient lcnThirdClient;

    @Override
    @LcnTransaction(propagation = DTXPropagation.REQUIRED)
    public void transfer(int money) {
        bankAMapper.insert(BankA.initial(-money));
        System.out.println("BankA转账: " + -money);
        System.out.println(TracingContext.tracing().groupId());
        lcnThirdClient.transfer(money);
        System.out.println("第三方BankA转账: " + -money);

        if (money >= 20 && money < 30) {
            throw new RuntimeException("server is error,money too large");
        }
    }
}
