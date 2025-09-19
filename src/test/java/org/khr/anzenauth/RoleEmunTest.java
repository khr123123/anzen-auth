package org.khr.anzenauth;

import org.junit.jupiter.api.Test;
import org.khr.anzenauth.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

/**
 @author KK
 @create 2025-09-19-15:14
 */
@SpringBootTest
public class RoleEmunTest {

    @Autowired
    private SysRoleService sysRoleService;
    @Test
    public void test() {
        Set<Long> rolePermission = sysRoleService.getRolePermission(1L);
        System.out.println("rolePermission = " + rolePermission);
    }

}
