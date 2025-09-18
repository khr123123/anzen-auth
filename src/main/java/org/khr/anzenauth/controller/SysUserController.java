package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.model.dto.UserLoginDto;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.model.entity.SysUser;
import org.khr.anzenauth.model.vo.LoginUserInfoVO;
import org.khr.anzenauth.security.properties.Anonymous;
import org.khr.anzenauth.security.service.SysPermissionService;
import org.khr.anzenauth.service.SysMenuService;
import org.khr.anzenauth.service.SysUserService;
import org.khr.anzenauth.utils.SecurityContextUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 用户表 控制层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@RestController
@RequestMapping("/sysUser")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    private final SysPermissionService permissionService;

    private final SysMenuService menuService;

    /**
     * 用户登录
     */
    @PostMapping("login")
    @Anonymous // 允许匿名访问
    public BaseResponse<String> userLogin(@RequestBody UserLoginDto userLoginDto) {
        return ResultUtils.success(sysUserService.userLogin(userLoginDto.getUsername(), userLoginDto.getPassword()));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public BaseResponse<LoginUserInfoVO> getLoginUserInfo() {
        Long loginUserId = SecurityContextUtils.getUserId();
        Set<String> allPermission = SecurityContextUtils.getAllPermission();
        SysUser sysUser = sysUserService.getById(loginUserId);
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser);
        return ResultUtils.success(
            LoginUserInfoVO.builder().user(sysUser).roles(roles).permissions(allPermission).build()
        );
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public BaseResponse<List<SysMenu>> getRouters() {
        Long loginUserId = SecurityContextUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(loginUserId);
        System.out.println("menus = " + menus);
        return ResultUtils.success(menus);
    }


    /**
     * 保存用户表。
     */
    @PostMapping("save")
    public BaseResponse<Boolean> save(@RequestBody SysUser sysUser) {
        return ResultUtils.success(sysUserService.save(sysUser));
    }

    /**
     * 根据主键删除用户表。
     */
    @DeleteMapping("remove/{id}")
    public BaseResponse<Boolean> remove(@PathVariable Long id) {
        return ResultUtils.success(sysUserService.removeById(id));
    }

    /**
     * 根据主键更新用户表。
     */
    @PutMapping("update")
    public BaseResponse<Boolean> update(@RequestBody SysUser sysUser) {
        return ResultUtils.success(sysUserService.updateById(sysUser));
    }

    /**
     * 查询所有用户表。
     */
    @GetMapping("list")
    public BaseResponse<List<SysUser>> list() {
        return ResultUtils.success(sysUserService.list());
    }

    /**
     * 根据主键获取用户表。
     */
    @GetMapping("getInfo/{id}")
    public BaseResponse<SysUser> getInfo(@PathVariable Long id) {
        return ResultUtils.success(sysUserService.getById(id));
    }

    /**
     * 分页查询用户表。
     */
    @GetMapping("page")
    public BaseResponse<Page<SysUser>> page(Page<SysUser> page) {
        return ResultUtils.success(sysUserService.page(page));
    }

}
