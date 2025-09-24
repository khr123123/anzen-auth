package org.khr.anzenauth.base.aop.log;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.khr.anzenauth.base.constant.BusinessStatus;
import org.khr.anzenauth.manager.async.AsyncFactory;
import org.khr.anzenauth.manager.async.AsyncManager;
import org.khr.anzenauth.model.entity.SysOperaLog;
import org.khr.anzenauth.base.utils.IpUtils;
import org.khr.anzenauth.base.utils.SecurityContextUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

/**
 * 操作日志切面
 *
 * @author kk
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /** 记录耗时 */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<>("Cost Time");

    @Before("@annotation(controllerLog)")
    public void doBefore(Log controllerLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    @AfterThrowing(pointcut = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    private void handleLog(JoinPoint joinPoint, Log controllerLog, Exception e, Object jsonResult) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            SysOperaLog operaLog = new SysOperaLog();
            operaLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            operaLog.setOperaIp(IpUtils.getIpAddr(request));
            operaLog.setOperaUrl(StrUtil.sub(request.getRequestURI(), 0, 255));
            operaLog.setOperaName(SecurityContextUtils.getUsername());
            operaLog.setOperaTime(new Date());
            operaLog.setMethod(
                joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            operaLog.setRequestMethod(request.getMethod());

            if (e != null) {
                operaLog.setStatus(BusinessStatus.FAIL.ordinal());
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                operaLog.setErrorMsg(StrUtil.sub(e.getMessage() != null ? e.getMessage() : sw.toString(), 0, 2000));
            }

            // 设置注解描述信息
            operaLog.setBusinessType(controllerLog.businessType().ordinal());
            operaLog.setTitle(controllerLog.title());

            // 请求参数
            if (controllerLog.isSaveRequestData()) {
                Map<String, String[]> paramMap = request.getParameterMap();
                if (!paramMap.isEmpty()) {
                    operaLog.setOperaParam(StrUtil.sub(JSONUtil.toJsonStr(paramMap), 0, 2000));
                }
            }

            // 返回结果
            if (controllerLog.isSaveResponseData() && ObjectUtil.isNotNull(jsonResult)) {
                operaLog.setJsonResult(StrUtil.sub(JSONUtil.toJsonStr(jsonResult), 0, 2000));
            }

            operaLog.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());
            AsyncManager.me().execute(AsyncFactory.recordOpera(operaLog));
            log.info("操作日志: {}", JSONUtil.toJsonStr(operaLog));

        } catch (Exception exp) {
            log.error("日志记录异常: {}", exp.getMessage(), exp);
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }
}
