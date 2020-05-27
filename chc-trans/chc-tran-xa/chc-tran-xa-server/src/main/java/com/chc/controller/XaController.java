package com.chc.controller;

import com.chc.conf.Result;
import com.chc.service.XaServerServiceImpl;
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
@Api(tags = "XA事务示例")
@RestController
@RequestMapping("xa")
public class XaController {
    @Resource(name = "xaServerService")
    XaServerServiceImpl accountApi;

    @ApiOperation(value = "XA分布式事务")
    @GetMapping("/transfer/{money}")
    public Result<String> declareTransfer(@PathVariable("money") int money) {
        accountApi.transfer(money);
        return Result.success();
    }


}
