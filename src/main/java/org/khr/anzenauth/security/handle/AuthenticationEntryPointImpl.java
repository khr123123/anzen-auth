package org.khr.anzenauth.security.handle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.khr.anzenauth.base.common.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        // 未登录或认证失败
        int code = ErrorCode.NOT_LOGIN_ERROR.getCode();
        String msg = "认证失败,无法访问系统资源," + ErrorCode.NOT_LOGIN_ERROR.getMessage();
        // 写 JSON 响应
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + code + ", \"message\":\"" + msg + "\"}");
    }
}
