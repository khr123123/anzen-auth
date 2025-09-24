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
    private static final int EXPIRE_MINUTES = 60; // 1 小时

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
}
