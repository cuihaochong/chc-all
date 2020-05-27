package com.chc.service;

import com.chc.api.AccountApi;
import com.chc.mapper.BankAMapper;
import com.chc.pojo.entity.BankA;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.codingapi.txlcn.tracing.TracingContext.tracing;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Service("lcnHttpService")
public class LcnHttpServiceImpl implements AccountApi {

    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    BankAMapper bankAMapper;

    @Override
    @LcnTransaction(propagation = DTXPropagation.REQUIRED)
    public void transfer(int money) {
        bankAMapper.insert(BankA.initial(-money));
        System.out.println("BankA转账: " + -money);
        System.out.println(TracingContext.tracing().groupId());

        String appList = tracing().appMapString();
        String groupId = tracing().groupId();
        String lcnParam = "&appList="+appList+"&groupId="+groupId;
        String url = "http://localhost:8081/lcn/http/transfer?money={money}"+lcnParam;
        restTemplate.postForObject(url, "money", String.class, money);
        System.out.println("第三方BankA转账: " + -money);

        if (money >= 20 && money < 30) {
            throw new RuntimeException("server is error,money too large");
        }
    }
}
