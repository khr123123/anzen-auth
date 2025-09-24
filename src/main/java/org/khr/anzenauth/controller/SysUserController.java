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
import org.khr.anzenauth.base.utils.SecurityContextUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping("loginUser")
    @Anonymous
    public BaseResponse<String> loginUser(@RequestBody UserLoginDto userLoginDto) {
        return ResultUtils.success(
            sysUserService.userLogin(userLoginDto.getUsername(), userLoginDto.getPassword())
        );
    }

    /**
     * 获取登录用户信息
     */
    @GetMapping("getLoginUserInfo")
    public BaseResponse<LoginUserInfoVO> getLoginUserInfo() {
        Long loginUserId = SecurityContextUtils.getUserId();
        Set<String> allPermission = SecurityContextUtils.getAllPermission();
        SysUser sysUser = sysUserService.getById(loginUserId);
        Set<String> roles = permissionService.getRolePermission(sysUser);
        return ResultUtils.success(
            LoginUserInfoVO.builder().user(sysUser).roles(roles).permissions(allPermission).build()
        );
    }

    /**
     * 获取登录用户路由信息
     */
    @GetMapping("getUserRouters")
    public BaseResponse<List<SysMenu>> getUserRouters() {
        Long loginUserId = SecurityContextUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(loginUserId, false);
        return ResultUtils.success(menus);
    }

    /**
     * 给指定用户授权角色
     *
     * @param id 用户ID
     * @param roles 角色ID列表
     * @return 操作结果
     */
    @PostMapping("/grantRole/{id}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public BaseResponse<Boolean> grantRole(@PathVariable Long id, @RequestParam(required = false) List<Long> roles) {
        boolean result = sysUserService.grantRole(id, roles);
        return ResultUtils.success(result);
    }


    /**
     * 获取指定用户的角色列表
     *
     * @param id 用户ID
     * @return 角色列表
     */
    @GetMapping("/getUserRoles/{id}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public BaseResponse<List<Long>> getUserRoles(@PathVariable Long id) {
        return ResultUtils.success(sysUserService.getUserRoles(id));
    }

    /**
     * 保存用户
     */
    @PostMapping("saveUser")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public BaseResponse<Boolean> saveUser(@RequestBody SysUser sysUser) {
        return ResultUtils.success(sysUserService.save(sysUser));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("deleteUser/{id}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public BaseResponse<Boolean> deleteUser(@PathVariable Long id) {
        return ResultUtils.success(sysUserService.removeById(id));
    }

    /**
     * 更新用户
     */
    @PutMapping("updateUser")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public BaseResponse<Boolean> updateUser(@RequestBody SysUser sysUser) {
        return ResultUtils.success(sysUserService.updateById(sysUser));
    }

    /**
     * 查询所有用户
     */
    @GetMapping("listUser")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public BaseResponse<List<SysUser>> listUser() {
        return ResultUtils.success(sysUserService.list());
    }

    /**
     * 获取单个用户信息
     */
    @GetMapping("getUserInfo/{id}")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public BaseResponse<SysUser> getUserInfo(@PathVariable Long id) {
        return ResultUtils.success(sysUserService.getById(id));
    }

    /**
     * 分页查询用户
     */
    @GetMapping("pageUser")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public BaseResponse<Page<SysUser>> pageUser(Page<SysUser> page) {
        return ResultUtils.success(sysUserService.page(page));
    }
}
