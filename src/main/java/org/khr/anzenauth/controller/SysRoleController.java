package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.khr.anzenauth.base.aop.log.Log;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.base.constant.BusinessType;
import org.khr.anzenauth.model.entity.SysRole;
import org.khr.anzenauth.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 角色表 控制层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@RestController
@RequestMapping("/sysRole")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    /**
     * 获取某角色权限。
     */
    @GetMapping("getRolePermission/{id}")
    @PreAuthorize("hasAuthority('sys:role:perm')")
    public BaseResponse<Set<Long>> getRolePermission(@PathVariable Long id) {
        return ResultUtils.success(sysRoleService.getRolePermission(id));
    }

    /**
     * 给某角色授权。
     */
    @PostMapping("grantRole/{id}")
    @PreAuthorize("hasAuthority('sys:role:perm')")
    public BaseResponse<Boolean> grantPermission(@PathVariable Long id,
        @RequestParam(required = false) List<Long> permissions) {
        return ResultUtils.success(sysRoleService.grantPermission(id, permissions));
    }

    /**
     * 保存角色。
     */
    @PostMapping("saveRole")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @Log(title = "保存角色", businessType = BusinessType.INSERT)
    public BaseResponse<Boolean> saveRole(@RequestBody SysRole sysRole) {
        return ResultUtils.success(sysRoleService.save(sysRole));
    }

    /**
     * 删除角色。
     */
    @DeleteMapping("deleteRole/{id}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Log(title = "删除角色", businessType = BusinessType.DELETE)
    public BaseResponse<Boolean> deleteRole(@PathVariable Long id) {
        return ResultUtils.success(sysRoleService.removeById(id));
    }

    /**
     * 更新角色。
     */
    @PutMapping("updateRole")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @Log(title = "更新角色", businessType = BusinessType.UPDATE)
    public BaseResponse<Boolean> updateRole(@RequestBody SysRole sysRole) {
        return ResultUtils.success(sysRoleService.updateById(sysRole));
    }

    /**
     * 查询所有角色。
     */
    @GetMapping("listRole")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public BaseResponse<List<SysRole>> listRole() {
        return ResultUtils.success(sysRoleService.list());
    }

    /**
     * 获取单个角色信息。
     */
    @GetMapping("getRoleInfo/{id}")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public BaseResponse<SysRole> getRoleInfo(@PathVariable Long id) {
        return ResultUtils.success(sysRoleService.getById(id));
    }

    /**
     * 分页查询角色。
     */
    @GetMapping("pageRole")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public BaseResponse<Page<SysRole>> pageRole(Page<SysRole> page) {
        return ResultUtils.success(sysRoleService.page(page));
    }
}
