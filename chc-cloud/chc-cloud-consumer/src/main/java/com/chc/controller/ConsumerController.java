package com.chc.controller;

import com.chc.client.FeignUserClient;
import com.chc.client.ZuulUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/11
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    FeignUserClient feignUserClient;
    @Autowired
    ZuulUserClient zuulUserClient;

    @GetMapping("/say/{str}")
    public String say(@PathVariable("str") String str) {
        return feignUserClient.say(str);
    }

    @GetMapping("/sing/{music}")
    public String sing(@PathVariable("music") String music) {
        return feignUserClient.sing(music);
    }
}
