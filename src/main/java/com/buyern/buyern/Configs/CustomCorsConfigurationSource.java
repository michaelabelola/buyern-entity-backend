package com.buyern.buyern.Configs;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class CustomCorsConfigurationSource implements CorsConfigurationSource {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(List.of(
//                "*",
                "http://localhost:[*]",
                "http://192.168.0.12:[*]",
                "http://127.0.0.1:[*]"
        ));
//             corsConfiguration.addAllowedOriginPattern("**");
//        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.HEAD);
        corsConfiguration.addAllowedMethod(HttpMethod.TRACE);
//             corsConfiguration.setAllowedMethods(List.of("POST", "GET", "OPTIONS", "DELETE"));
        return corsConfiguration;
    }
}
