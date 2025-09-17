package org.khr.anzenauth.common.properties;

import java.lang.annotation.*;

/**
 * 匿名访问不鉴权注解
 *
 * @author kk
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {

}
