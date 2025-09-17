package org.khr.anzenauth;

import org.junit.jupiter.api.Test;
import org.khr.anzenauth.service.impl.SysMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

/**
 @author KK
 @create 2025-09-17-11:51
 */
@SpringBootTest
public class MenuTest {

    @Autowired
    private SysMenuServiceImpl service;

    @Test
    public void testSelectMenuPermsByUserId() {
        Set<String> menuPerms = service.selectMenuPermsByUserId(1L);
        System.out.println(menuPerms);
    }

}
