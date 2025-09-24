package org.khr.anzenauth.api.addressApi;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Spring HTTP Interface
 */
@HttpExchange(url = "http://whois.pconline.com.cn")
public interface AddressHttpClient {

    @GetExchange("/ipJson.jsp")
    String getIpInfo(@RequestParam("ip") String ip, @RequestParam("json") boolean json);
}