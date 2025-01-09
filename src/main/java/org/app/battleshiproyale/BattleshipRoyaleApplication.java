package org.app.battleshiproyale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BattleshipRoyaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BattleshipRoyaleApplication.class, args);
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") 
                    .allowedOrigins("http://localhost:3000", 
                        "https://battle-ship-royale.web.app")  
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                    .allowedHeaders("*")  
                    .allowCredentials(true); 
        }
    }
}