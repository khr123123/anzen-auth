package org.khr.anzenauth.controller;

import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.monitor.Server;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author kk
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @PreAuthorize("hasAuthority('sys:monitor:query')")
    @GetMapping
    public BaseResponse<Server> getInfo() {
        Server server = new Server();
        server.copyTo();
        return ResultUtils.success(server);
    }
}
