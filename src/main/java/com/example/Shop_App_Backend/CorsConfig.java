package com.example.Shop_App_Backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // CORS mapping for "/shoe/**"
        registry.addMapping("/shoe/**") // Update the mapping to match your endpoint
                .allowedOrigins("http://localhost:5173") // Update the allowed origin with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Add the allowed HTTP methods
                .allowedHeaders("*"); // Add the allowed headers

        // CORS mapping for "/suggestion/**"
        registry.addMapping("/suggestion/**") // Update the mapping to match your endpoint
                .allowedOrigins("http://localhost:5173") // Update the allowed origin with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Add the allowed HTTP methods
                .allowedHeaders("*"); // Add the allowed headers
    }

}
