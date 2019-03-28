package com.ylzinfo.bib.core.log;

import com.alibaba.fastjson.JSON;
import com.ylzinfo.bib.core.springutils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.InputStreamSource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/********************************************************************************
 *
 * Title: aop日志处理
 *
 * Description:
 *
 * @author chenlm
 * create date on 2019/3/21
 *
 *******************************************************************************/
@Aspect
@Order(0)
@Slf4j
public class WebLogAOP {
    @Pointcut("execution(public * com.ylzinfo.bib.*..*Controller.*(..))")
    public void webLog() {
    }

    @Pointcut("execution(public * com.ylzinfo.bib.*.exception..*Handler.*(..))")
    public void webExcepLog() {
    }


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = SpringUtils.getSpringRequest();
        log.info("请求地址 : " + request.getRequestURL().toString());
        log.info("请求方法 : " + request.getMethod());
        log.info("IP地址 : " + request.getRemoteAddr());
        log.info("处理方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求数据: " + getRequestJsonString(joinPoint));
    }

    public String getRequestJsonString(JoinPoint joinPoint) {
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

    @AfterReturning(returning = "ret", pointcut = "webExcepLog()")
    public void doAfterExceptionReturning(Object ret) {
        doAfterReturning(ret);

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("响应数据 : " + JSON.toJSONString(ret));
    }

}
