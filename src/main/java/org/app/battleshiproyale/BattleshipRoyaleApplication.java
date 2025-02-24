package org.app.battleshiproyale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class BattleshipRoyaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BattleshipRoyaleApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://localhost:3000");  
        config.addAllowedOrigin("https://battle-ship-royale.vercel.app/");
        config.addAllowedOrigin("https://battle-ship-royale.vercel.app");
        config.addAllowedHeader("*"); 
        config.addAllowedMethod("GET");   
        config.addAllowedMethod("POST");  
        config.addAllowedMethod("PUT");  
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
