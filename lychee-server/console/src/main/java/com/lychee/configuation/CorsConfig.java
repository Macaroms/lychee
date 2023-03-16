package com.lychee.configuation;

import com.lychee.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/4/10 12:42
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        return new CorsFilter();
    }

    @Bean
    public FilterRegistrationBean corsFilterRegistration(CorsFilter corsFilter){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(corsFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
