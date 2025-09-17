package org.khr.anzenauth;

import org.junit.jupiter.api.Test;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.controller.SysUserController;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.model.entity.SysUser;
import org.khr.anzenauth.model.vo.RouterVO;
import org.khr.anzenauth.utils.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

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
        BaseResponse<List<SysMenu>> routers = sysUserController.getRouters();
        System.out.println("routers.getData() = " + routers.getData());
    }

}
