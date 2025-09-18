package org.khr.anzenauth.utils;

import lombok.experimental.UtilityClass;
import org.khr.anzenauth.base.common.ErrorCode;
import org.khr.anzenauth.base.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.khr.anzenauth.model.entity.table.SysUserTableDef.SYS_USER;

/**
 * 安全服务工具类
 *
 * @author kk
 */
@UtilityClass
public class SecurityContextUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Set<String> getAllPermission() {
        return getAuthentication().getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    /**
     * 用户ID
     **/
    public static Long getUserId() {
        Object principal = getAuthenticationPrincipal();
        if (principal instanceof Map<?, ?> map) {
            Object userId = map.get(SYS_USER.USER_ID);
            if (userId instanceof Number) {
                return ((Number) userId).longValue();
            }
        }
        throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "获取用户ID异常");
    }

    /**
     * 获取用户名
     **/
    public static String getUsername() {
        Object principal = getAuthenticationPrincipal();
        if (principal instanceof Map<?, ?> map) {
            Object username = map.get(SYS_USER.USERNAME);
            if (username != null) {
                return username.toString();
            }
        }
        throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "获取用户账户异常");
    }

    /**
     * 获取 principal
     */
    private static Object getAuthenticationPrincipal() {
        Authentication auth = getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "用户未登录或认证信息为空");
        }
        return auth.getPrincipal();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     */
    public static String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    /**
     * 判断密码是否相同
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 验证用户是否具备某权限
     */
    public static boolean hasPermission(String permission) {
        return getAllPermission().contains(permission);
    }

}
