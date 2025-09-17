package org.khr.anzenauth.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.anzenauth.model.entity.SysRoleMenu;
import org.khr.anzenauth.mapper.SysRoleMenuMapper;
import org.khr.anzenauth.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表 服务层实现。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>  implements SysRoleMenuService{

}
