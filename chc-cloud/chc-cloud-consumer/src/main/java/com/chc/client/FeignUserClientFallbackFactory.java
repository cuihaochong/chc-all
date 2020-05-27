package com.chc.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/11
 */
@Component
public class FeignUserClientFallbackFactory implements FallbackFactory<FeignUserClient> {
    @Override
    public FeignUserClient create(Throwable throwable) {
        return new FeignUserClient() {

            @Override
            public String say(String str) {
                return "Feign用户服务-say调用超时";
            }

            @Override
            public String sing(String music) {
                return "Feign用户服务-sing调用超时";
            }
        };
    }
}
