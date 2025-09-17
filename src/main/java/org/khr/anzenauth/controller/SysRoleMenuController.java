package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.anzenauth.common.common.BaseResponse;
import org.khr.anzenauth.common.common.ResultUtils;
import org.khr.anzenauth.model.entity.SysRoleMenu;
import org.khr.anzenauth.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色和菜单关联表 控制层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@RestController
@RequestMapping("/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 保存角色和菜单关联表。
     */
    @PostMapping("save")
    public BaseResponse<Boolean> save(@RequestBody SysRoleMenu sysRoleMenu) {
        return ResultUtils.success(sysRoleMenuService.save(sysRoleMenu));
    }

    /**
     * 根据主键删除角色和菜单关联表。
     */
    @DeleteMapping("remove/{id}")
    public BaseResponse<Boolean> remove(@PathVariable Long id) {
        return ResultUtils.success(sysRoleMenuService.removeById(id));
    }

    /**
     * 根据主键更新角色和菜单关联表。
     */
    @PutMapping("update")
    public BaseResponse<Boolean> update(@RequestBody SysRoleMenu sysRoleMenu) {
        return ResultUtils.success(sysRoleMenuService.updateById(sysRoleMenu));
    }

    /**
     * 查询所有角色和菜单关联表。
     */
    @GetMapping("list")
    public BaseResponse<List<SysRoleMenu>> list() {
        return ResultUtils.success(sysRoleMenuService.list());
    }

    /**
     * 根据主键获取角色和菜单关联表。
     */
    @GetMapping("getInfo/{id}")
    public BaseResponse<SysRoleMenu> getInfo(@PathVariable Long id) {
        return ResultUtils.success(sysRoleMenuService.getById(id));
    }

    /**
     * 分页查询角色和菜单关联表。
     */
    @GetMapping("page")
    public BaseResponse<Page<SysRoleMenu>> page(Page<SysRoleMenu> page) {
        return ResultUtils.success(sysRoleMenuService.page(page));
    }

}
