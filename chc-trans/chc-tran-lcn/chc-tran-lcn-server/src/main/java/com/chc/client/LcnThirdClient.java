package com.chc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/13
 */
@FeignClient(name = "lcn-third")
public interface LcnThirdClient {

    @GetMapping("lcn/third/transfer/{money}")
    void transfer(@PathVariable("money") int money);
}
