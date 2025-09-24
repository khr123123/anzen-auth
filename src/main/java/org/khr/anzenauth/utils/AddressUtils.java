package org.khr.anzenauth.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 地址工具类
 *
 * @author kk
 */
@Slf4j
public class AddressUtils {

    /** IP 地址查询接口 */
    private static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    /** 未知地址 */
    private static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        // 内网 IP 直接返回
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ip", ip);
            params.put("json", true);

            String rspStr = HttpUtil.get(IP_URL, params);
            if (StrUtil.isBlank(rspStr)) {
                log.warn("获取地理位置失败，IP: {}", ip);
                return UNKNOWN;
            }

            JSONObject obj = JSONUtil.parseObj(rspStr);
            String region = obj.getStr("pro", "");
            String city = obj.getStr("city", "");
            return StrUtil.isBlank(region) && StrUtil.isBlank(city)
                ? UNKNOWN
                : String.format("%s %s", region, city);

        } catch (Exception e) {
            log.error("获取地理位置异常，IP: {}", ip, e);
            return UNKNOWN;
        }
    }
}
