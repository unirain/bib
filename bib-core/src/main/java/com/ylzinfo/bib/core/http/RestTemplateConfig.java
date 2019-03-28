package com.ylzinfo.bib.core.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/********************************************************************************
 *
 * Title: REST接口请求设置
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/8/17
 *
 *******************************************************************************/
@Configuration
public class RestTemplateConfig {
    /**
     * 连接设置
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(60000);
        factory.setReadTimeout(120000);
        return new RestTemplate(factory);
    }

}
