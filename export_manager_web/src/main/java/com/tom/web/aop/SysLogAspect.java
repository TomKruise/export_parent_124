package com.tom.web.aop;

import com.tom.domain.system.SysLog;
import com.tom.domain.system.User;
import com.tom.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class SysLogAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;

    @Around("execution(* com.tom.web.controller..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        boolean isNull = true;

        Signature signature = pjp.getSignature();

        Method targetMethod = ((MethodSignature) signature).getMethod();

        if (targetMethod.isAnnotationPresent(RequestMapping.class)) {
            SysLog log = new SysLog();

            User user = (User) request.getSession().getAttribute("loginUser");

            isNull = null == user ? true : false;

            log.setUserName(isNull ? "匿名" : user.getUserName());

            log.setIp(request.getRemoteAddr());

            log.setTime(new Date());

            log.setMethod(targetMethod.getName());

            RequestMapping requestMapping = targetMethod.getAnnotation(RequestMapping.class);

            String name = requestMapping.name();

            log.setAction(null == name ? "" : name);

            log.setCompanyId(isNull ? "" : user.getCompanyId());

            log.setCompanyName(isNull ? "" : user.getCompanyName());

            sysLogService.save(log);
        }

        return pjp.proceed();
    }
}
