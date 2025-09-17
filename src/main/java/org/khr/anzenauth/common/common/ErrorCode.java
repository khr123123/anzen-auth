package org.khr.anzenauth.common.common;

import lombok.Getter;

/**
 * 自定义错误码
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "成功"),
    PARAMS_ERROR(40000, "リクエストパラメータが無効"),
    NOT_LOGIN_ERROR(40100, "ログインが必要"),
    NO_AUTH_ERROR(40101, "権限がない"),
    NOT_FOUND_ERROR(40400, "リクエストしたデータが見つからない"),
    TOO_MANY_REQUEST(42900, "リクエストが多すぎ"),
    FORBIDDEN_ERROR(40300, "アクセスが禁止されている"),
    SYSTEM_ERROR(50000, "システム内部エラー"),
    OPERATION_ERROR(50001, "操作に失敗した");


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