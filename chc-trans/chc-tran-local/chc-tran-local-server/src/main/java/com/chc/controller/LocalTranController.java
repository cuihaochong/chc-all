package com.chc.controller;

import com.chc.api.AccountApi;
import com.chc.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Api(tags = "本地事务示例")
@RestController
@RequestMapping("tran")
public class LocalTranController {
    @Resource(name = "localTranService")
    AccountApi localTranService;
    @Autowired
    PlatformTransactionManager txManager;
    @Autowired
    TransactionTemplate transactionTemplate;

    @ApiOperation(value = "1.spring声明式事务")
    @PostMapping("/spring/transfer/declare")
    @Transactional
    public Result<String> declareTransfer(int money) {
        localTranService.transfer(money);
        return Result.success();
    }

    @ApiOperation(value = "2.spring编程式事务")
    @PostMapping("/spring/transfer/code")
    public Result<String> springCodeTransfer(int money) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                localTranService.transfer(money);
            }
        });
        return Result.success();
    }

    @ApiOperation(value = "3.java编程式事务")
    @PostMapping("/java/transfer/code")
    public Result<String> javaCodeTransfer(int money) {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        try {
            localTranService.transfer(money);
            txManager.commit(status);
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e.getMessage());
        }
        return Result.success();
    }
}
