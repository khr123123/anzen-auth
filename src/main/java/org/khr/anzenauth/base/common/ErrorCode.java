package org.khr.anzenauth.base.common;

import lombok.Getter;

/**
 * 自定义错误码
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "成功"),
    PARAMS_ERROR(40000, "请求参数无效"),
    NOT_LOGIN_ERROR(40100, "需要登录"),
    NO_AUTH_ERROR(40101, "没有权限"),
    NOT_FOUND_ERROR(40400, "请求的数据未找到"),
    TOO_MANY_REQUEST(42900, "请求过多"),
    FORBIDDEN_ERROR(40300, "访问被禁止"),
    SYSTEM_ERROR(50000, "系统内部错误"),
    OPERATION_ERROR(50001, "操作失败");


    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
