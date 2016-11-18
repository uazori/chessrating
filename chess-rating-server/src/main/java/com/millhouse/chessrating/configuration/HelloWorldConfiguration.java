package com.millhouse.chessrating.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Millhouse on 11/17/2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.millhouse.chessrating")
public class HelloWorldConfiguration  {
}
