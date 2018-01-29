package com.tattoosoft.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.tattoosoft.resource", "com.tattoosoft.business" })
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {
    //
}
