package org.khr.anzenauth.base.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.khr.anzenauth.model.entity.table.SysUserTableDef.SYS_USER;

@UtilityClass
public class TokenUtil {

    // 秘钥，可以放到配置文件里
    private static final String SECRET = "my-secret-key-by-kk";
    // token 有效期（分钟）
    public static final int EXPIRE_MINUTES = 60; // 1 小时
    // 触发续期的阈值（分钟）
    private static final int REFRESH_THRESHOLD_MINUTES = 20;

    /**
     * 生成 token
     *
     * @param userName 用户名
     * @param userId   用户ID
     * @return token 字符串
     */
    public static String generateToken(String userName, Long userId) {
        DateTime now = DateTime.now();
        DateTime expireAt = now.offsetNew(DateField.MINUTE, EXPIRE_MINUTES);
        Map<String, Object> payload = new HashMap<>();
        payload.put(JWTPayload.ISSUED_AT, now);
        payload.put(JWTPayload.EXPIRES_AT, expireAt);
        payload.put(JWTPayload.NOT_BEFORE, now);
        payload.put(SYS_USER.USERNAME.toString(), userName);
        payload.put(SYS_USER.USER_ID.toString(), userId);
        return JWTUtil.createToken(payload, SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 验证 token 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token).setSigner(JWTSignerUtil.hs256(SECRET.getBytes(StandardCharsets.UTF_8)));
            // 签名校验
            if (!jwt.verify()) {
                return false;
            }
            // 过期校验
            return !isTokenExpired(jwt);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断 token 是否过期
     */
    private static boolean isTokenExpired(JWT jwt) {
        Date exp = jwt.getPayloads().getDate(JWTPayload.EXPIRES_AT);
        return exp != null && exp.before(new Date());
    }

    /**
     * 从 token 中获取指定字段
     */
    public static Object getClaim(String token, String key) {
        return JWTUtil.parseToken(token).getPayload(key);
    }


    /**
     * 如果 token 快要过期，则生成一个新的 token（滑动过期）
     * @param token 旧 token
     * @return 新 token（如果无需刷新则返回旧 token）
     */
    public static String refreshTokenIfNeeded(String token) {
        JWT jwt = JWTUtil.parseToken(token)
            .setSigner(JWTSignerUtil.hs256(SECRET.getBytes(StandardCharsets.UTF_8)));
        if (!jwt.verify()) {
            throw new RuntimeException("Token 签名不合法");
        }
        Date exp = jwt.getPayloads().getDate(JWTPayload.EXPIRES_AT);
        if (exp == null) {
            throw new RuntimeException("Token 缺少过期时间");
        }
        long millisLeft = exp.getTime() - System.currentTimeMillis();
        long minutesLeft = millisLeft / 1000 / 60;
        // 如果剩余时间小于阈值，则生成新 token
        if (minutesLeft <= REFRESH_THRESHOLD_MINUTES) {
            String userName = (String) jwt.getPayload(SYS_USER.USERNAME.toString());
            Long userId = Long.valueOf(jwt.getPayload(SYS_USER.USER_ID.toString()).toString());
            return generateToken(userName, userId);
        }
        return token;
    }
}
