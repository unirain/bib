package com.ylzinfo.bib.core.log;

import com.alibaba.fastjson.JSON;
import com.ylzinfo.bib.core.springutils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/********************************************************************************
 *
 * Title: aop日志跟踪
 *
 * Description:
 *
 * @author chenlm
 * create date on 2019/3/21
 *
 *******************************************************************************/
@Aspect
@Order(-1)
@Slf4j
@Component
public class WebLogAOP {
    @Pointcut("execution(public * com.ylzinfo.bib.*..*Controller.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        boolean ignore = ignoreLog(((MethodSignature) joinPoint.getSignature()).getMethod());
        try {
            doBeforeLog(joinPoint, ignore);
            result = joinPoint.proceed();
        } finally {
            doAfter(result, ignore);
        }
        return result;


    }

    /**
     * 是否忽略日志
     *
     * @param method
     * @return
     */
    private boolean ignoreLog(Method method) {
        IgnoreLog ignoreLog = method.getAnnotation(IgnoreLog.class);
        if (ignoreLog == null) {
            return false;
        } else {
            return ignoreLog.value();
        }

    }

    private String getRequestJsonString(JoinPoint joinPoint) {
        //是否有流类型
        //传出去
        List list = new ArrayList();
        Iterator iterator = Arrays.asList(joinPoint.getArgs()).iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            //值太大 不过滤
            if (!(o instanceof InputStreamSource) && !(o instanceof ServletResponse) && !(o instanceof ServletRequest)) {
                list.add(o);
            }
        }
        return processMessageInfo(list);
    }

    /**
     * 返回解析的返回值
     *
     * @param ret
     * @return
     */
    private String processMessageInfo(Object ret) {
        String message;
        try {
            message = JSON.toJSONString(ret);
        } catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }

    /**
     * 切入前
     *
     * @param joinPoint
     */
    private void doBeforeLog(ProceedingJoinPoint joinPoint, boolean ignore) {
        HttpServletRequest request = SpringUtils.getSpringRequest();
        log.info("请求地址 : " + request.getRequestURL().toString());
        log.info("请求方法 : " + request.getMethod());
        log.info("IP地址 : " + request.getRemoteAddr());
        log.info("处理方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        if (ignore) {
            log.info("此方法不输出请求数据！");
        } else {
            log.info("请求数据: " + getRequestJsonString(joinPoint));
        }
    }

    /**
     * 切入后
     *
     * @param o
     */
    private void doAfter(Object o, boolean ignore) {
        if (ignore) {
            return;
        }
        if (!Objects.isNull(o)) {
            log.info("响应数据 : " + JSON.toJSONString(o));
        } else {
            log.info("响应数据 : null");
        }
    }


}
