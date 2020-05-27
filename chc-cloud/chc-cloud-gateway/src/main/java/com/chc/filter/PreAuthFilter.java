package com.chc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: Auth过滤器
 *
 * @author cuihaochong
 * @date 2019/9/12
 */
@Component
@ConfigurationProperties(prefix = "zuul.filter")
public class PreAuthFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(PreAuthFilter.class);

    /**
     * 不被拦截的方法
     */
    private List<String> ignoredMethods = new ArrayList<>();

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 获取当前请求的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String req = request.getRequestURI();
        logger.info("===uri:{}", req);
        if (StringUtils.isBlank(req)) {
            return true;
        }
        if (ignoredMethods.size() > 0) {
            // 判断请求是否属于不被拦截的方法,不被拦截返回false,拦截返回true
            return ignoredMethods.stream().noneMatch(req::contains);
        }
        // 其余请求均被拦截
        return true;
    }

    @Override
    public Object run() {
        // 获取当前请求的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            currentContext.setResponseBody("token is null");
        } else {
            // token值,真实情况从redis中获取,此处为模拟
            String redisToken = "123";
            if (!redisToken.equals(token)) {
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                currentContext.setResponseBody("token is error");
            }
        }
        return null;
    }

    public List<String> getIgnoredMethods() {
        return ignoredMethods;
    }

    public void setIgnoredMethods(List<String> ignoredMethods) {
        this.ignoredMethods = ignoredMethods;
    }
}
