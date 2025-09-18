package org.khr.anzenauth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.anzenauth.mapper.SysRoleMapper;
import org.khr.anzenauth.model.entity.SysRole;
import org.khr.anzenauth.model.entity.SysUserRole;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

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

}
