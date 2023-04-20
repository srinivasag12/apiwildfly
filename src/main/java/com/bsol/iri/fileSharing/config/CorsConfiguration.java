package com.bsol.iri.fileSharing.config;

/**
 * 
 * @author rupesh
 *	Enabling CORS
 */

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EntityScan("com.bsol.iri.fileSharing")
@EnableJpaRepositories({"com.bsol.iri.fileSharing"})
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
@ComponentScan ("com.bsol.iri.fileSharing")
public class CorsConfiguration 
{
    @Bean
    public WebMvcConfigurer corsConfigurer() 
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	 registry.addMapping("/**")
                 .allowedOrigins("*")
                 .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "DELETE", "PATCH","OPTIONS")
                 .allowCredentials(true);
            }
        };
    }
}