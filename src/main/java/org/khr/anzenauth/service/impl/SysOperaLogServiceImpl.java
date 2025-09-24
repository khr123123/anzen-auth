package org.khr.anzenauth.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.anzenauth.model.entity.SysOperaLog;
import org.khr.anzenauth.mapper.SysOperaLogMapper;
import org.khr.anzenauth.service.SysOperaLogService;
import org.springframework.stereotype.Service;

/**
 * 操作日志记录 服务层实现。
 *
 * @author KK
 * @since 2025-09-24 13:17:17
 */
@Service
public class SysOperaLogServiceImpl extends ServiceImpl<SysOperaLogMapper, SysOperaLog>  implements SysOperaLogService{

}
