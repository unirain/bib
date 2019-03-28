package com.ylzinfo.bib.core.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/********************************************************************************
 *
 * Title: MybatisPlus 配置
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/7/3
 *
 *******************************************************************************/
@Configuration
public class MybatisPlusConfig {
    /**
     * oracle  seq
     *
     * @return
     */
    @Bean
    public IKeyGenerator keyGenerator() {
        return new OracleKeyGenerator();
    }


}
