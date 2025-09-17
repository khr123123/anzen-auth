package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.anzenauth.common.common.BaseResponse;
import org.khr.anzenauth.common.common.ResultUtils;
import org.khr.anzenauth.model.entity.SysUserRole;
import org.khr.anzenauth.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户和角色关联表 控制层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 保存用户和角色关联表。
     */
    @PostMapping("save")
    public BaseResponse<Boolean> save(@RequestBody SysUserRole sysUserRole) {
        return ResultUtils.success(sysUserRoleService.save(sysUserRole));
    }

    /**
     * 根据主键删除用户和角色关联表。
     */
    @DeleteMapping("remove/{id}")
    public BaseResponse<Boolean> remove(@PathVariable Long id) {
        return ResultUtils.success(sysUserRoleService.removeById(id));
    }

    /**
     * 根据主键更新用户和角色关联表。
     */
    @PutMapping("update")
    public BaseResponse<Boolean> update(@RequestBody SysUserRole sysUserRole) {
        return ResultUtils.success(sysUserRoleService.updateById(sysUserRole));
    }

    /**
     * 查询所有用户和角色关联表。
     */
    @GetMapping("list")
    public BaseResponse<List<SysUserRole>> list() {
        return ResultUtils.success(sysUserRoleService.list());
    }

    /**
     * 根据主键获取用户和角色关联表。
     */
    @GetMapping("getInfo/{id}")
    public BaseResponse<SysUserRole> getInfo(@PathVariable Long id) {
        return ResultUtils.success(sysUserRoleService.getById(id));
    }

    /**
     * 分页查询用户和角色关联表。
     */
    @GetMapping("page")
    public BaseResponse<Page<SysUserRole>> page(Page<SysUserRole> page) {
        return ResultUtils.success(sysUserRoleService.page(page));
    }
}
