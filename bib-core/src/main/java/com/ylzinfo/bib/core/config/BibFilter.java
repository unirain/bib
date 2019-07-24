package com.ylzinfo.bib.core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/********************************************************************************
 *
 * Title: 过滤器
 *
 * Description:
 *
 * author chenlm
 * create date on 2018/4/23
 * Copyright:Copyright © 2017-2018
 *
 *******************************************************************************/
@Configuration
public class BibFilter {
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequstFilter());//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
        registration.setName("RequstFilter");
        registration.setOrder(1);//设置优先级
        return registration;
    }

    public class RequstFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest hreq = (HttpServletRequest) servletRequest;
            String reqMethod = hreq.getMethod();
            String transfer=hreq.getHeader("transfer-encoding");
            if ((HttpMethod.GET.name().equals(reqMethod))||"chunked".equals(transfer)) {
                //get请求直接放行
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(hreq);
                filterChain.doFilter(requestWrapper, response);
            }
        }

        @Override
        public void destroy() {

        }
    }


}
