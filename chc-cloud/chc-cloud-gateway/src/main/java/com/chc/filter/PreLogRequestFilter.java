package com.chc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 日志过滤器
 *
 * @author cuihaochong
 * @date 2019/9/12
 */
@Component
public class PreLogRequestFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(PreLogRequestFilter.class);

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
        return true;
    }

    @Override
    public Object run() {
        // 获取当前请求的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        logger.info(String.format("send %s request to %s", request.getMethod(),
            request.getRequestURL().toString()));
        return null;
    }
}
