package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.service.SysMenuService;
import org.khr.anzenauth.utils.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单表 控制层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询所有菜单。
     */
    @GetMapping("listMenu2Tree")
    public BaseResponse<List<SysMenu>> listMenu2Tree() {
        return ResultUtils.success(sysMenuService.listMenu2Tree());
    }

    /**
     * 查询用户菜单树（包含按钮）
     */
    @PostMapping("getUserMenuTreeWithButton")
    public BaseResponse<List<SysMenu>> getUserMenuTreeWithButton() {
        return ResultUtils.success(
            sysMenuService.selectMenuTreeByUserId(SecurityContextUtils.getUserId(), true)
        );
    }

    /**
     * 查询用户菜单树（不包含按钮）
     */
    @PostMapping("getUserMenuTree")
    public BaseResponse<List<SysMenu>> getUserMenuTree() {
        return ResultUtils.success(
            sysMenuService.selectMenuTreeByUserId(SecurityContextUtils.getUserId(), false)
        );
    }

    /**
     * 保存菜单。
     */
    @PostMapping("saveMenu")
    public BaseResponse<Boolean> saveMenu(@RequestBody SysMenu sysMenu) {
        return ResultUtils.success(sysMenuService.save(sysMenu));
    }

    /**
     * 删除菜单。
     */
    @DeleteMapping("deleteMenu/{id}")
    public BaseResponse<Boolean> deleteMenu(@PathVariable Long id) {
        return ResultUtils.success(sysMenuService.removeById(id));
    }

    /**
     * 更新菜单。
     */
    @PutMapping("updateMenu")
    public BaseResponse<Boolean> updateMenu(@RequestBody SysMenu sysMenu) {
        return ResultUtils.success(sysMenuService.updateById(sysMenu));
    }

    /**
     * 查询所有菜单。
     */
    @GetMapping("listMenu")
    public BaseResponse<List<SysMenu>> listMenu() {
        return ResultUtils.success(sysMenuService.list());
    }

    /**
     * 获取单个菜单信息。
     */
    @GetMapping("getMenuInfo/{id}")
    public BaseResponse<SysMenu> getMenuInfo(@PathVariable Long id) {
        return ResultUtils.success(sysMenuService.getById(id));
    }

    /**
     * 分页查询菜单。
     */
    @GetMapping("pageMenu")
    public BaseResponse<Page<SysMenu>> pageMenu(Page<SysMenu> page) {
        return ResultUtils.success(sysMenuService.page(page));
    }
}
