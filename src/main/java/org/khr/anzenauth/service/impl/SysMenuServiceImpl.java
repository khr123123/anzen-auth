package org.khr.anzenauth.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.anzenauth.mapper.SysUserMapper;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.mapper.SysMenuMapper;
import org.khr.anzenauth.service.SysMenuService;
import org.khr.anzenauth.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
            .select("m.perms")
            .from("sys_menu").as("m")
            .leftJoin("sys_role_menu").as("rm").on("rm.menu_id = m.menu_id")
            .leftJoin("sys_user_role").as("ur").on("rm.role_id = ur.role_id")
            .leftJoin("sys_role").as("r").on("r.role_id = ur.role_id")
            .where("ur.user_id = ?", userId);

        return listAs(wrapper, String.class)
            .stream()
            .filter(s -> s != null && !s.isEmpty())
            .flatMap(s -> Arrays.stream(s.split(",")))
            .collect(Collectors.toSet());
    }


}
