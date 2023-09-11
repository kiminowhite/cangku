package com.knw.config;

import com.knw.filter.LoginCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-08 09:27
 */
@Configuration
public class ServletConfig {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Bean
    public FilterRegistrationBean filterRegistrationBean()
    {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        LoginCheckFilter loginCheckFilter = new LoginCheckFilter();
        loginCheckFilter.setStringRedisTemplate(stringRedisTemplate);
        filterRegistrationBean.setFilter(loginCheckFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return  filterRegistrationBean;
    }
}
