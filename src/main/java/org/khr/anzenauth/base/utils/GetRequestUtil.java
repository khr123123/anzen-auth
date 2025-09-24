package org.khr.anzenauth.base.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 @author KK
 @create 2025-09-24-15:45
 */
@UtilityClass
public class GetRequestUtil {


    public static HttpServletRequest get() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
