package com.chc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Service("xaServerService")
public class XaServerServiceImpl{

    @Autowired
    @Qualifier("tran1JdbcTemplate")
    private JdbcTemplate tran1JdbcTemplate;

    @Autowired
    @Qualifier("tran2JdbcTemplate")
    private JdbcTemplate tran2JdbcTemplate;

    @Transactional
    public String transfer(int money) {
        int resultJames = tran1JdbcTemplate.update("INSERT INTO bank_a(money,user_name)VALUES (?,?)",-money,"james");
        int resultPeter = tran2JdbcTemplate.update("INSERT INTO bank_b(money,user_name)VALUES (?,?)",money,"peter");
        if (money > 20){
            throw new RuntimeException("money too large");//系统宕机了怎么办？
        }
        return "success";
    }
}
