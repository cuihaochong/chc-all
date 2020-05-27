package com.chc.client;

import com.chc.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Description: Zuul用户服务客户端调用接口
 *
 * @author cuihaochong
 * @date 2019/9/16
 */
@FeignClient(name = "api-gateway", configuration = FeignConfig.class,
    fallbackFactory = ZuulUserClientFallbackFactory.class)
public interface ZuulUserClient {

    @GetMapping("/chc/user-server/user/say/{str}")
    String say(@PathVariable("str") String str);

    @GetMapping("/chc/user-server/user/sing/{music}")
    String sing(@PathVariable("music") String music);
}
