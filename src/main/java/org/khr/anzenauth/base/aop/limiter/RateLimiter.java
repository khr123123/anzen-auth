package org.khr.anzenauth.base.aop.limiter;


import org.khr.anzenauth.base.constant.LimitType;
import org.khr.anzenauth.base.constant.RedisConstants;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author kk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     */
    String key() default RedisConstants.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;
}
