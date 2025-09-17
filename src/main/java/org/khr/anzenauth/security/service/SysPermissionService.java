package org.khr.anzenauth.security.service;

import lombok.AllArgsConstructor;
import org.khr.anzenauth.base.constant.RoleConstant;
import org.khr.anzenauth.model.entity.SysUser;
import org.khr.anzenauth.service.SysMenuService;
import org.khr.anzenauth.service.SysRoleService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author ruoyi
 */
@Component
@AllArgsConstructor
public class SysPermissionService {

    private final SysRoleService roleService;

    private final SysMenuService menuService;

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (roleService.isAdmin(user.getUserId())) {
            roles.add(RoleConstant.ROLE_ADMIN);
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (roleService.isAdmin(user.getUserId())) {
            perms.add("*:*:*");
        } else {
            perms = menuService.selectMenuPermsByUserId(user.getUserId());
        }
        return perms;
    }
}
