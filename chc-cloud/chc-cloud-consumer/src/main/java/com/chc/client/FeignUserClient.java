package com.chc.client;

import com.chc.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/11
 */
@FeignClient(name = "user-provider", configuration = FeignConfig.class, fallbackFactory = FeignUserClientFallbackFactory.class)
public interface FeignUserClient {

    /**
     * 说话
     *
     * @param str 要说的话
     * @return 说的话
     */
    @GetMapping("/user/say/{str}")
    String say(@PathVariable("str") String str);

    /**
     * 唱歌
     *
     * @param music 音乐
     * @return 唱歌了
     */
    @GetMapping("/user/sing/{music}")
    String sing(@PathVariable("music") String music);
}
