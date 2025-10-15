package org.khr.anzenauth;

import com.mybatisflex.core.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.khr.anzenauth.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.khr.anzenauth.model.entity.table.SysMenuTableDef.SYS_MENU;
import static org.khr.anzenauth.model.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static org.khr.anzenauth.model.entity.table.SysRoleTableDef.SYS_ROLE;
import static org.khr.anzenauth.model.entity.table.SysUserRoleTableDef.SYS_USER_ROLE;

/**
 @author KK
 @create 2025-09-17-13:36
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private SysMenuService sysMenuService;

    @Test
    public void selectMenuPermsByUserId() {
        QueryWrapper wrapper = QueryWrapper.create()
            .select(SYS_MENU.PERMS)
            .from(SYS_MENU)
            .leftJoin(SYS_ROLE_MENU).on(SYS_MENU.MENU_ID.eq(SYS_ROLE_MENU.MENU_ID))
            .leftJoin(SYS_USER_ROLE).on(SYS_ROLE_MENU.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .leftJoin(SYS_ROLE).on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .where(SYS_USER_ROLE.USER_ID.eq(1));
        List<String> strings = sysMenuService.listAs(wrapper, String.class);
        System.out.println("strings = " + strings);
    }
}
