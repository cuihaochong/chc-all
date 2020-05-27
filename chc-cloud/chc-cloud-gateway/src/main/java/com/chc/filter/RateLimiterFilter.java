package com.chc.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 方法限流
 *
 * @author cuihaochong
 * @date 2019/9/12
 */
@Component
public class RateLimiterFilter extends ZuulFilter {

    /**
     * 每秒产生1000个令牌
     */
    private final RateLimiter RATE_LIMITER = RateLimiter.create(1000);
    private final Logger logger = LoggerFactory.getLogger(RateLimiterFilter.class);

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
        //只对订单接口限流
        String rateLimiterUrl = "/chc/user-server/user/say";
        return rateLimiterUrl.equalsIgnoreCase(request.getRequestURI());
    }

    @Override
    public Object run() {
        // 获取当前请求的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //就相当于每调用一次tryAcquire()方法，令牌数量减1，当1000个用完后，那么后面进来的用户无法访问上面接口
        //当然这里只写类上面一个接口，可以这么写，实际可以在这里要加一层接口判断。
        if (!RATE_LIMITER.tryAcquire()) {
            currentContext.setSendZuulResponse(false);
            //HttpStatus.TOO_MANY_REQUESTS.value()里面有静态代码常量
            currentContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        return null;
    }

}
