package org.khr.anzenauth.controller;

import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.monitor.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 服务器监控
 *
 * @author kk
 */
@RestController
@RequestMapping("/monitor")
public class ServerController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @PreAuthorize("hasAuthority('sys:monitor:query')")
    @GetMapping("server")
    public BaseResponse<Server> getInfo() {
        Server server = new Server();
        server.copyTo();
        return ResultUtils.success(server);
    }


    @PreAuthorize("hasAuthority('sys:redis:query')")
    @GetMapping("redis")
    public BaseResponse<Map<String, Object>> redisCacheList() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) connection ->
            connection.serverCommands().info()
        );
        Properties commandStats = redisTemplate.execute((RedisCallback<Properties>) connection ->
            connection.serverCommands().info("commandstats")
        );
        Long dbSize = redisTemplate.execute((RedisCallback<Long>) connection ->
            connection.serverCommands().dbSize()
        );
        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);
        // 解析 commandstats
        List<Map<String, String>> pieList = new ArrayList<>();
        if (commandStats != null) {
            for (String key : commandStats.stringPropertyNames()) {
                String property = commandStats.getProperty(key);
                if (property != null) {
                    Map<String, String> data = new HashMap<>(2);
                    // cmdstat_get => get
                    data.put("name", key.replaceFirst("cmdstat_", ""));
                    // calls=xxx,usec=xxx
                    String value = property.split(",")[0].replace("calls=", "");
                    data.put("value", value);
                    pieList.add(data);
                }
            }
        }
        result.put("commandStats", pieList);
        return ResultUtils.success(result);
    }

}
