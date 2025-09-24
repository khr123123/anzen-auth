package org.khr.anzenauth.api.addressApi;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.khr.anzenauth.base.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 地址工具类
 * 使用 Spring 6 HttpInterface Client 改造
 *
 * @author kk
 */
@Slf4j
@Component
public class AddressApiClient {

    /** 未知地址 */
    private static final String UNKNOWN = "XX XX";

    @Autowired
    private AddressHttpClient httpClient;

    public String getRealAddressByIP(String ip) {
        // 内网 IP 直接返回
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            // 调用远程接口
            String rspStr = httpClient.getIpInfo(ip, true);
            if (StrUtil.isBlank(rspStr)) {
                log.warn("获取地理位置失败，IP: {}", ip);
                return UNKNOWN;
            }
            JSONObject obj = JSONUtil.parseObj(rspStr);
            String region = obj.getStr("pro", "");
            String city = obj.getStr("city", "");

            log.info("获取地理位置成功，IP: {}, 地理位置: {}", ip, region + " " + city);

            return StrUtil.isBlank(region) && StrUtil.isBlank(city)
                ? UNKNOWN
                : String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常，IP: {}", ip, e);
            return UNKNOWN;
        }
    }

}
