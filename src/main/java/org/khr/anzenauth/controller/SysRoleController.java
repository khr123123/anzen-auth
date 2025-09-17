package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.model.entity.SysRole;
import org.khr.anzenauth.security.properties.Anonymous;
import org.khr.anzenauth.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色表 控制层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 保存角色表。
     */
    @PostMapping("save")
    public BaseResponse<Boolean> save(@RequestBody SysRole sysRole) {
        return ResultUtils.success(sysRoleService.save(sysRole));
    }

    /**
     * 根据主键删除角色表。
     */
    @DeleteMapping("remove/{id}")
    public BaseResponse<Boolean> remove(@PathVariable Long id) {
        return ResultUtils.success(sysRoleService.removeById(id));
    }

    /**
     * 根据主键更新角色表。
     */
    @PutMapping("update")
    public BaseResponse<Boolean> update(@RequestBody SysRole sysRole) {
        return ResultUtils.success(sysRoleService.updateById(sysRole));
    }

    /**
     * 查询所有角色表。
     */
    @GetMapping("list")
    @Anonymous //
    public BaseResponse<List<SysRole>> list() {
        return ResultUtils.success(sysRoleService.list());
    }

    /**
     * 根据主键获取角色表。
     */
    @GetMapping("getInfo/{id}")
    public BaseResponse<SysRole> getInfo(@PathVariable Long id) {
        return ResultUtils.success(sysRoleService.getById(id));
    }

    /**
     * 分页查询角色表。
     */
    @GetMapping("page")
    public BaseResponse<Page<SysRole>> page(Page<SysRole> page) {
        return ResultUtils.success(sysRoleService.page(page));
    }
}
