package com.chc.service;

import com.chc.api.AccountApi;
import com.chc.mapper.BankBMapper;
import com.chc.pojo.entity.BankB;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Service("tccThirdService")
public class TccThirdServiceImpl implements AccountApi {
    @Autowired
    BankBMapper bankBMapper;

    @Override
    @TccTransaction(confirmMethod = "confirm", cancelMethod = "cancel", executeClass = TccThirdServiceImpl.class)
    public void transfer(int money) {

        bankBMapper.insert(BankB.initial(money));
        System.out.println("BankB收账: " + money);
        System.out.println(TracingContext.tracing().groupId());
        if (money >= 30) {
            throw new RuntimeException("third server is error : money too large");
        }
    }

    //确认一下
    public void confirm(int money) {
        String groupId = TracingContext.tracing().groupId();
        // TODO 确认一下,当前业务场景无需处理

    }

    //取消一下
    public void cancel(int money) {
        String groupId = TracingContext.tracing().groupId();
        // 进行补偿
        bankBMapper.insert(BankB.initial(-money));
        System.out.println("BankB收账: " + -money);
        System.out.println(TracingContext.tracing().groupId());
    }
}
