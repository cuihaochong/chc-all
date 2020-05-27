package com.chc.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Description: Api网关熔断处理,可以根据对应每个代理的服务分别做熔断
 *
 * @author cuihaochong
 * @date 2019/9/12
 */
@Component
public class ZuulFallback implements FallbackProvider {

    private final Logger logger = LoggerFactory.getLogger(FallbackProvider.class);

    /**
     * The route this fallback will be used for.
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause != null) {
            String reason = cause.getMessage();
            logger.info("zuul熔断异常: {}", reason);
        }
        return new ClientHttpResponse() {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "text/html; charset=UTF-8");
                return headers;
            }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream((route + " is unavailable.").getBytes());
            }

            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public int getRawStatusCode() {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() {
                return HttpStatus.BAD_REQUEST.getReasonPhrase();
            }

            @Override
            public void close() {
            }
        };
    }
}
