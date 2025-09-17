package org.khr.anzenauth.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.anzenauth.model.entity.SysUserRole;
import org.khr.anzenauth.mapper.SysUserRoleMapper;
import org.khr.anzenauth.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表 服务层实现。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>  implements SysUserRoleService{

}
