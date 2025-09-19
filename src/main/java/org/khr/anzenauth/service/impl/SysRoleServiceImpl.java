package org.khr.anzenauth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.khr.anzenauth.mapper.SysRoleMapper;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.model.entity.SysRole;
import org.khr.anzenauth.model.entity.SysRoleMenu;
import org.khr.anzenauth.model.entity.SysUserRole;
import org.khr.anzenauth.service.SysMenuService;
import org.khr.anzenauth.service.SysRoleMenuService;
import org.khr.anzenauth.service.SysRoleService;
import org.khr.anzenauth.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.khr.anzenauth.base.constant.RoleConstant.ROLE_ADMIN_ID;
import static org.khr.anzenauth.model.entity.table.SysMenuTableDef.SYS_MENU;
import static org.khr.anzenauth.model.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static org.khr.anzenauth.model.entity.table.SysRoleTableDef.SYS_ROLE;
import static org.khr.anzenauth.model.entity.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static org.khr.anzenauth.model.entity.table.SysUserTableDef.SYS_USER;

/**
 * 角色表 服务层实现。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysUserRoleService sysUserRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysMenuService sysMenuService;

    @Override
    public boolean isAdmin(Long userId) {
        if (userId == null) {
            return false;
        }
        return sysUserRoleService.exists(
            query().eq(SysUserRole::getUserId, userId).eq(SysUserRole::getRoleId, ROLE_ADMIN_ID));
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        if (userId == null) {
            return Set.of();
        }
        QueryWrapper wrapper = query()
            .select(SYS_ROLE.ROLE_KEY)
            .from(SYS_ROLE)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_ROLE.USER_ID))
            .where(SYS_USER.USER_ID.eq(userId));

        return listAs(wrapper, String.class).stream().filter(StrUtil::isNotBlank).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getRolePermission(Long id) {
        return sysRoleMenuService.listAs(query().select(SYS_ROLE_MENU.MENU_ID).eq(SYS_ROLE_MENU.ROLE_ID.getName(), id),
            Long.class).stream().filter(ObjectUtil::isNotNull).collect(Collectors.toSet());
    }

    @Override
    public Boolean grantPermission(Long id, List<Long> permissions) {
        // 校验 permissions 是否存在
        if (permissions != null && !permissions.isEmpty()) {
            // 在 sys_menu 表里校验 menu_id 是否存在
            long count = sysMenuService.count(query().in(SysMenu::getMenuId, permissions));
            if (count != permissions.size()) {
                throw new IllegalArgumentException("存在无效权限ID");
            }
        }

        // 先删除原来的角色菜单关联
        sysRoleMenuService.remove(query().eq(SysRoleMenu::getRoleId, id));

        // 保存新的角色权限
        if (permissions != null && !permissions.isEmpty()) {
            List<SysRoleMenu> roleMenus = permissions.stream()
                .map(menuId -> new SysRoleMenu(id, menuId))
                .toList();
            return sysRoleMenuService.saveBatch(roleMenus);
        }

        return true;
    }

}
