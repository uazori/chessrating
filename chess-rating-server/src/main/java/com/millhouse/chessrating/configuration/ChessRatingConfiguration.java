package com.millhouse.chessrating.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 11/17/2016.
 * Configuration for chess rating project
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.millhouse.chessrating")
public class ChessRatingConfiguration {
}
