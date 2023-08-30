package com.example.einkaufsliste.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

//@Configuration
//@EnableWebMvc
public class WebConfig {
    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;

//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
////        config.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost"));
//        config.setAllowedOriginPatterns(List.of("*"));
////        config.addAllowedOrigin("http://localhost:4200");
////        config.addAllowedOrigin("http://localhost");
//        config.setAllowedHeaders(Arrays.asList(
//                HttpHeaders.AUTHORIZATION,
//                HttpHeaders.CONTENT_TYPE,
//                HttpHeaders.ACCEPT));
//        config.setAllowedMethods(Arrays.asList(
//                HttpMethod.GET.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.DELETE.name(),
//                HttpMethod.OPTIONS.name()));
//        config.setMaxAge(MAX_AGE);
//
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//
//        // should be set order to -100 because we need to CorsFilter before SpringSecurityFilter
//        bean.setOrder(CORS_FILTER_ORDER);
//        return bean;
//    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
////        configuration.setAllowedOriginPatterns(List.of("*"));
//        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost", "http://localhost:80"));
//        configuration.addAllowedHeader("*");
//        configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.DELETE.name(),
//                HttpMethod.OPTIONS.name()));
//        configuration.setMaxAge(MAX_AGE);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//
//        // should be set order to -100 because we need to CorsFilter before SpringSecurityFilter
//        bean.setOrder(CORS_FILTER_ORDER);
//
//        return source;
//    }

//    @Bean
//    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                // ...
//                .cors(cors -> cors.disable());
//        return http.build();
//    }
}
