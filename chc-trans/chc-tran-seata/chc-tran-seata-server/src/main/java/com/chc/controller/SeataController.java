package com.chc.controller;

import com.chc.api.AccountApi;
import com.chc.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@Api(tags = "Seata事务示例")
@RestController
@RequestMapping("seata")
public class SeataController {
    @Resource(name = "seataService")
    AccountApi accountApi;

    @ApiOperation(value = "Seatac分布式事务")
    @GetMapping("/transfer")
    public Result<String> declareTransfer(int money) {
        accountApi.transfer(money);
        return Result.success();
    }


}
