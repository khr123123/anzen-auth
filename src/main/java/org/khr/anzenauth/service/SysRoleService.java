package org.khr.anzenauth.service;

import com.mybatisflex.core.service.IService;
import org.khr.anzenauth.model.entity.SysRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色表 服务层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
public interface SysRoleService extends IService<SysRole> {

    boolean isAdmin(Long userId);

    Set<String> selectRolePermissionByUserId(Long userId);

    Set<Long> getRolePermission(Long id);

    Boolean grantPermission(Long id, List<Long> permissions);
}
