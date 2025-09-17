package org.khr.anzenauth.service.impl;

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

import static org.khr.anzenauth.base.constant.RoleConstant.ROLE_ADMIN_ID;

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
    public Collection<String> selectRolePermissionByUserId(Long userId) {
        return List.of();
    }

}
