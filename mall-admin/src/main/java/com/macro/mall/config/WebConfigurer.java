package com.macro.mall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@ComponentScan
@Configuration
public class WebConfigurer implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String paths="C:\\Users\\sf621\\Desktop\\mall\\images\\";
        String paths="/usr/local/images/";
        registry.addResourceHandler("/images/**").addResourceLocations("file:///"+paths);
    }
}
