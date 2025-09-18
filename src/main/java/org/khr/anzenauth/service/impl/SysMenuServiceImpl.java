package org.khr.anzenauth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.khr.anzenauth.base.constant.MenuConstant;
import org.khr.anzenauth.mapper.SysMenuMapper;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.service.SysMenuService;
import org.khr.anzenauth.utils.SecurityContextUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.khr.anzenauth.model.entity.table.SysMenuTableDef.SYS_MENU;
import static org.khr.anzenauth.model.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static org.khr.anzenauth.model.entity.table.SysRoleTableDef.SYS_ROLE;
import static org.khr.anzenauth.model.entity.table.SysUserRoleTableDef.SYS_USER_ROLE;

@Slf4j
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
            .leftJoin(SYS_ROLE_MENU)
            .on(SYS_MENU.MENU_ID.eq(SYS_ROLE_MENU.MENU_ID))
            .leftJoin(SYS_USER_ROLE).on(SYS_ROLE_MENU.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .leftJoin(SYS_ROLE).on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .where(SYS_USER_ROLE.USER_ID.eq(userId));

        return listAs(wrapper, String.class).stream().filter(StrUtil::isNotBlank).collect(Collectors.toSet());
    }

    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus;
        if (SecurityContextUtils.isAdmin(userId)) {
            menus = list(query().in(SYS_MENU.MENU_TYPE.getName(), MenuConstant.MENU_M, MenuConstant.MENU_C));
        } else {
            menus = list(
                query()
                    .leftJoin(SYS_ROLE_MENU).on(SYS_MENU.MENU_ID.eq(SYS_ROLE_MENU.MENU_ID))
                    .leftJoin(SYS_USER_ROLE).on(SYS_ROLE_MENU.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
                    .leftJoin(SYS_ROLE).on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
                    .where(SYS_USER_ROLE.USER_ID.eq(userId))
                    .in(SYS_MENU.MENU_TYPE.getName(), MenuConstant.MENU_M, MenuConstant.MENU_C));
        }
        return buildTree(menus, 0L);
    }

    /** 递归构建菜单树 */
    private List<SysMenu> buildTree(List<SysMenu> menus, Long parentId) {
        return menus.stream()
            .filter(m -> Objects.equals(m.getParentId(), parentId))
            .peek(m -> m.setChildren(this.buildTree(menus, m.getMenuId())))
            .collect(Collectors.toList());
    }

}
