package org.khr.anzenauth;

import org.junit.jupiter.api.Test;
import org.khr.anzenauth.controller.SysUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 @author KK
 @create 2025-09-17-15:32
 */
@SpringBootTest
public class TestTree {

    @Autowired
    private SysUserController sysUserController;

    @Test
    public void testTree() {
//        BaseResponse<SysUser> info = sysUserController.getInfo(1L);
//        System.out.println("info.getData() = " + info.getData());
//        BaseResponse<List<SysMenu>> routers = sysUserController.getRouters();
//        System.out.println("routers.getData() = " + routers.getData());
    }

}
