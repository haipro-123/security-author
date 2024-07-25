package com.example.demo.config;

import com.example.demo.entity.Permission;
import com.example.demo.entity.RoleUser;
import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.service.IPermissionService;
import com.example.demo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

/**
 * cấu hình security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;
    //config authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HandlerMappingIntrospector introspector, HttpSecurity httpSecurity) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        List<Permission> permissionList = permissionService.getListPermission();
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(cors -> corsConfigurationSource());
        httpSecurity.authorizeHttpRequests((authorize)->{
            authorize.requestMatchers(mvc.pattern(Constant.URL_LOGIN)).permitAll();
            authorize.requestMatchers(mvc.pattern(Constant.URL_LOGOUT)).permitAll();
            authorize.requestMatchers(mvc.pattern(Constant.URL_REGISTER)).permitAll();
            authorize.requestMatchers(mvc.pattern(Constant.URL_CHECK_OTP)).permitAll();
            for(Permission permission : permissionList) {
                authorize.requestMatchers(mvc.pattern(HttpMethod.valueOf(permission.getHttpMethod().getHttpMethodName()),permission.getEndpoint())).hasAnyRole(permission.getListRole().stream().map(RoleUser::getRoleName).toArray(String[]::new));
            }
            authorize.anyRequest().authenticated();
        });
        httpSecurity.addFilterBefore(customAuthenticationFilter, AuthorizationFilter.class);
        httpSecurity.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
    //config password encode
    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();
    }
    //config cors
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // allow all origins
        configuration.addAllowedMethod("*"); // allow all methods (GET, POST, PUT, DELETE, etc)
        configuration.addAllowedHeader("*"); // allow all headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
