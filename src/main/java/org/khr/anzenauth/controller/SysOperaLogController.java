package org.khr.anzenauth.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ResultUtils;
import org.khr.anzenauth.model.entity.SysOperaLog;
import org.khr.anzenauth.service.SysOperaLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作日志记录 控制层。
 *
 * @author KK
 * @since 2025-09-24 14:36:17
 */
@RestController
@RequestMapping("/sysOperaLog")
public class SysOperaLogController {

    @Autowired
    private SysOperaLogService sysOperaLogService;

    /**
     * 查询所有操作日志记录。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:log:query')")
    public BaseResponse<List<SysOperaLog>> logList() {
        return ResultUtils.success(sysOperaLogService.list());
    }

    /**
     * 根据主键获取操作日志记录。
     *
     * @param id 操作日志记录主键
     * @return 操作日志记录详情
     */
    @GetMapping("getInfo/{id}")
    @PreAuthorize("hasAuthority('sys:log:query')")
    public BaseResponse<SysOperaLog> getLogInfo(@PathVariable Long id) {
        return ResultUtils.success(sysOperaLogService.getById(id));

    }

    /**
     * 分页查询操作日志记录。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:log:query')")
    public BaseResponse<Page<SysOperaLog>> logPage(Page<SysOperaLog> page) {
        return ResultUtils.success(sysOperaLogService.page(page));
    }

}
