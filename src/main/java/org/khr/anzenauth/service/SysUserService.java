package org.khr.anzenauth.service;

import com.mybatisflex.core.service.IService;
import org.khr.anzenauth.model.dto.UserLoginDto;
import org.khr.anzenauth.model.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * 用户表 服务层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
public interface SysUserService extends IService<SysUser> {

    String userLogin(String username, String password);

    boolean grantRole(Long id, List<Long> roles);

    List<Long> getUserRoles(Long id);
}
