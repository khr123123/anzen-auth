package org.khr.anzenauth.manager.async;

import org.khr.anzenauth.api.addressApi.AddressApiClient;
import org.khr.anzenauth.base.utils.SpringUtil;
import org.khr.anzenauth.model.entity.SysOperaLog;
import org.khr.anzenauth.service.SysOperaLogService;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author kk
 */
public class AsyncFactory {

//    /**
//     * 记录登录信息
//     *
//     * @param username 用户名
//     * @param status 状态
//     * @param message 消息
//     * @param args 列表
//     * @return 任务task
//     */
//    public static TimerTask recordLogininfor(final String username, final String status, final String message,
//        final Object... args) {
//        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        final String ip = IpUtils.getIpAddr(request);
//        return new TimerTask() {
//            @Override
//            public void run() {
//                String address = AddressUtils.getRealAddressByIP(ip);
//                StringBuilder s = new StringBuilder();
//                s.append(LogUtils.getBlock(ip));
//                s.append(address);
//                s.append(LogUtils.getBlock(username));
//                s.append(LogUtils.getBlock(status));
//                s.append(LogUtils.getBlock(message));
//                // 打印信息到日志
//                sys_user_logger.info(s.toString(), args);
//                // 获取客户端操作系统
//                String os = userAgent.getOperatingSystem().getName();
//                // 获取客户端浏览器
//                String browser = userAgent.getBrowser().getName();
//                // 封装对象
//                SysLogininfor logininfor = new SysLogininfor();
//                logininfor.setUserName(username);
//                logininfor.setIpaddr(ip);
//                logininfor.setLoginLocation(address);
//                logininfor.setBrowser(browser);
//                logininfor.setOs(os);
//                logininfor.setMsg(message);
//                // 日志状态
//                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
//                    logininfor.setStatus(Constants.SUCCESS);
//                } else if (Constants.LOGIN_FAIL.equals(status)) {
//                    logininfor.setStatus(Constants.FAIL);
//                }
//                // 插入数据
//                SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
//            }
//        };
//    }

    /**
     * 操作日志记录
     *
     * @param operaLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOpera(final SysOperaLog operaLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operaLog.setOperaLocation(
                    SpringUtil.getBean(AddressApiClient.class).getRealAddressByIP(operaLog.getOperaIp()));
                SpringUtil.getBean(SysOperaLogService.class).save(operaLog);
            }
        };
    }
}
