package com.chc.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Description: Zuul用户服务客户端熔断处理
 *
 * @author cuihaochong
 * @date 2019/9/16
 */
@Component
public class ZuulUserClientFallbackFactory implements FallbackFactory<FeignUserClient> {
    @Override
    public FeignUserClient create(Throwable throwable) {
        return new FeignUserClient() {

            @Override
            public String say(String str) {
                return "Zuul用户服务-say调用超时";
            }

            @Override
            public String sing(String music) {
                return "Zuul用户服务-sing调用超时";
            }
        };
    }
}
