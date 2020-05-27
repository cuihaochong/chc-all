package com.chc.controller;

import com.chc.api.AccountApi;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@RestController
@RequestMapping("/lcn/http")
public class LcnHttpController {
    @Resource(name = "lcnThirdService")
    AccountApi accountApi;

    @GetMapping("transfer")
    public void transfer(@RequestParam("money") int money) {
        accountApi.transfer(money);
    }

}
