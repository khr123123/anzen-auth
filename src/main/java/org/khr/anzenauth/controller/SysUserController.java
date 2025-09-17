package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.base.properties.Anonymous;
import org.khr.anzenauth.model.dto.UserLoginDto;
import org.khr.anzenauth.model.entity.SysUser;
import org.khr.anzenauth.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户表 控制层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     */
    @PostMapping("login")
    @Anonymous // 允许匿名访问
    public BaseResponse<String> userLogin(@RequestBody UserLoginDto userLoginDto) {
        return ResultUtils.success(sysUserService.userLogin(userLoginDto.getUsername(), userLoginDto.getPassword()));
    }

    /**
     * 保存用户表。
     */
    @PostMapping("save")
    public BaseResponse<Boolean> save(@RequestBody SysUser sysUser) {
        return ResultUtils.success(sysUserService.save(sysUser));
    }

    /**
     * 根据主键删除用户表。
     */
    @DeleteMapping("remove/{id}")
    public BaseResponse<Boolean> remove(@PathVariable Long id) {
        return ResultUtils.success(sysUserService.removeById(id));
    }

    /**
     * 根据主键更新用户表。
     */
    @PutMapping("update")
    public BaseResponse<Boolean> update(@RequestBody SysUser sysUser) {
        return ResultUtils.success(sysUserService.updateById(sysUser));
    }

    /**
     * 查询所有用户表。
     */
    @GetMapping("list")
    public BaseResponse<List<SysUser>> list() {
        return ResultUtils.success(sysUserService.list());
    }

    /**
     * 根据主键获取用户表。
     */
    @GetMapping("getInfo/{id}")
    public BaseResponse<SysUser> getInfo(@PathVariable Long id) {
        return ResultUtils.success(sysUserService.getById(id));
    }

    /**
     * 分页查询用户表。
     */
    @GetMapping("page")
    public BaseResponse<Page<SysUser>> page(Page<SysUser> page) {
        return ResultUtils.success(sysUserService.page(page));
    }

}
