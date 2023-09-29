package com.patrick.inventorymanagementtask.security.api;

import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author patrick on 2/8/20
 * @project  inventory
 */
@Configuration
@Order(1)
@EnableWebSecurity
public class ApiSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private ShopAuthenticationProvider  shopAuthenticationProvider;

    @Autowired
    @Qualifier("customUserDetailsService")
    private CustomUserDetailsService userDetailsService;

    @Autowired
    TokenHelper tokenHelper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .antMatcher("/api/**")
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                .and()
                .exceptionHandling().authenticationEntryPoint( authenticationEntryPoint )
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/login","/api/v1/signup","/api/v1/verify-phone",
                        "/api/v1/verify-otp","/api/v1/flutter-webhook","/api/v1/flutter-mpesa-stk",
                        "/api/v1/migrate-category",
                        "/api/v1/migrate-products",
                        "/api/v1/ussd",
                        "/api/v1/open/*",
                        "/api/v1/password/*",
                        "/api/v1/mpesa/callback/*",
                        "/api/v1/marketing/login",
                        "/actuator",
                        "/metrics","/api/v1/file/upload/*"
                ).permitAll()
                .anyRequest().authenticated()
                .and()

               .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, userDetailsService), UsernamePasswordAuthenticationFilter.class);
                http.csrf().disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(shopAuthenticationProvider);
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("X-CSRF-TOKEN", "authorization", "content-type", "x-auth-token","Access-Key"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

