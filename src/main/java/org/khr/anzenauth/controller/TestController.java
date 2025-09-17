package org.khr.anzenauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.service.SysMenuService;
import org.khr.anzenauth.utils.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 测试用 Controller
 * 带权限校验
 *
 * @author KK
 * @create 2025-09-17-11:57
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Tag(name = "body参数")
public class TestController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 测试根据 userId 查询菜单权限
     * 只有拥有 "user:view" 权限的用户才能访问
     *
     * @param userId 用户ID
     * @return BaseResponse 封装菜单权限集合
     */
    @PreAuthorize("hasAuthority('test:view')") // 权限校验
    @GetMapping("/menuPerms/{userId}")
    @Operation(summary = "普通body请求")
    public BaseResponse<Set<String>> testMenuPerms(@PathVariable Long userId) {
        Set<String> perms = sysMenuService.selectMenuPermsByUserId(userId);
        return ResultUtils.success(perms);
    }

    @GetMapping("/test")
    public BaseResponse<Set<String>> testMenuPerms() {
        Set<String> perms = sysMenuService.selectMenuPermsByUserId(1L);
        log.error("sd{}", SecurityContextUtils.getUsername());
        log.error("asd{}", SecurityContextUtils.getUserId());
        log.error("asd{}", SecurityContextUtils.getAllPermission());
        return ResultUtils.success(perms);
    }
}
