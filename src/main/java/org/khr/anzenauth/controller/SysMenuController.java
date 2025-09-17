package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.anzenauth.common.common.BaseResponse;
import org.khr.anzenauth.common.common.ResultUtils;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.service.SysMenuService;
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
     * 保存菜单表。
     */
    @PostMapping("save")
    public BaseResponse<Boolean> save(@RequestBody SysMenu sysMenu) {
        return ResultUtils.success(sysMenuService.save(sysMenu));
    }

    /**
     * 根据主键删除菜单表。
     */
    @DeleteMapping("remove/{id}")
    public BaseResponse<Boolean> remove(@PathVariable Long id) {
        return ResultUtils.success(sysMenuService.removeById(id));
    }

    /**
     * 根据主键更新菜单表。
     */
    @PutMapping("update")
    public BaseResponse<Boolean> update(@RequestBody SysMenu sysMenu) {
        return ResultUtils.success(sysMenuService.updateById(sysMenu));
    }

    /**
     * 查询所有菜单表。
     */
    @GetMapping("list")
    public BaseResponse<List<SysMenu>> list() {
        return ResultUtils.success(sysMenuService.list());
    }

    /**
     * 根据主键获取菜单表。
     */
    @GetMapping("getInfo/{id}")
    public BaseResponse<SysMenu> getInfo(@PathVariable Long id) {
        return ResultUtils.success(sysMenuService.getById(id));
    }

    /**
     * 分页查询菜单表。
     */
    @GetMapping("page")
    public BaseResponse<Page<SysMenu>> page(Page<SysMenu> page) {
        return ResultUtils.success(sysMenuService.page(page));
    }
}
