package org.khr.anzenauth.base.common;

import lombok.experimental.UtilityClass;

/**
 * 响应结果工具类
 * <p>
 * 提供统一的接口响应结果构建方法，封装了成功与失败的返回格式。
 * 使用 @UtilityClass 注解表示该类为工具类，所有方法都是静态的，
 * 并且该类无法被实例化。
 * <p>
 * 例如：
 * ResultUtils.success(data); // 成功返回
 * ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR); // 失败返回
 * </p>
 */
@UtilityClass
public class ResultUtils {

    /**
     * 构建成功的响应结果
     *
     * @param data 返回的数据对象
     * @param <T>  数据类型
     * @return BaseResponse 包装的成功结果，code=0，message="ok"
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 构建失败的响应结果
     *
     * @param errorCode 错误码枚举
     * @return BaseResponse 包装的失败结果，code=errorCode.code, message=errorCode.message
     */
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 构建失败的响应结果
     *
     * @param code    错误码
     * @param message 错误信息
     * @return BaseResponse 包装的失败结果
     */
    public static BaseResponse<?> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    /**
     * 构建失败的响应结果
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误信息，覆盖枚举中的默认信息
     * @return BaseResponse 包装的失败结果
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }
}
