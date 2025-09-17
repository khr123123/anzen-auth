package org.khr.anzenauth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.anzenauth.mapper.SysMenuMapper;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static org.khr.anzenauth.model.entity.table.SysMenuTableDef.SYS_MENU;
import static org.khr.anzenauth.model.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static org.khr.anzenauth.model.entity.table.SysRoleTableDef.SYS_ROLE;
import static org.khr.anzenauth.model.entity.table.SysUserRoleTableDef.SYS_USER_ROLE;

/**
 * 菜单表 服务层实现。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        if (userId == null) {
            return Set.of();
        }

        QueryWrapper wrapper = query()
            .select(SYS_MENU.PERMS)
            .from(SYS_MENU)
            .leftJoin(SYS_ROLE_MENU).on(SYS_MENU.MENU_ID.eq(SYS_ROLE_MENU.MENU_ID))
            .leftJoin(SYS_USER_ROLE).on(SYS_ROLE_MENU.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .leftJoin(SYS_ROLE).on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .where(SYS_USER_ROLE.USER_ID.eq(userId));

        return listAs(wrapper, String.class)
            .stream()
            .filter(StrUtil::isNotBlank)
            .collect(Collectors.toSet());
    }


}
