package org.khr.anzenauth;

import org.junit.jupiter.api.Test;
import org.khr.anzenauth.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

/**
 @author KK
 @create 2025-09-18-08:55
 */
@SpringBootTest
public class RoleTest {


    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void testIsAdmin() {
        Set<String> strings = sysRoleService.selectRolePermissionByUserId(1L);
        strings.forEach(System.out::println);
    }

}
