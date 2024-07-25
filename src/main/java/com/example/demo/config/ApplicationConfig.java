package com.example.demo.config;

import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;
    //set filter to bean
    @Bean
    public FilterRegistrationBean<CustomAuthenticationFilter> filterRegistrationBean() {
        FilterRegistrationBean<CustomAuthenticationFilter> registration = new FilterRegistrationBean<>(customAuthenticationFilter);
        registration.addUrlPatterns(Constant.URL_PATTERN_AUTHENTICATION_FILTER); // URL patterns to apply the filter to
        return registration;
    }
}
