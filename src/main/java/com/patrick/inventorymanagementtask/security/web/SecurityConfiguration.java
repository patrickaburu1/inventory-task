package com.patrick.inventorymanagementtask.security.web;


import com.patrick.inventorymanagementtask.config.AjaxRedirectFilter;
import com.patrick.inventorymanagementtask.security.api.ShopAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@Order(2)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ShopAuthenticationProvider shopAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/verify-phone").permitAll()
                .antMatchers("/verify-otp").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/forgot-password").permitAll()
                .antMatchers("/new-password").permitAll()
                .antMatchers("/backend/api/**").permitAll()
                .antMatchers("/tool/**").permitAll()
                .antMatchers( "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/actuator",
                        "/actuator/*",
                        "/metrics"
                        ).permitAll()
                .antMatchers("/new-password/*").permitAll().anyRequest()
                .authenticated().and()
                .addFilterBefore(ajaxRedirectFilter(), FilterSecurityInterceptor.class)
                .formLogin().loginPage("/login")
                .successHandler(successHandler())
                .failureUrl("/login?error")
                .and().logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout").permitAll()
                .and().exceptionHandling().accessDeniedPage("/access_denied")

                .and().csrf().ignoringAntMatchers("/api/***").disable()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login?expired");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/main/**", "/assets/**", "/js/**",  "/images/**");
    }


    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(shopAuthenticationProvider);
    }

    @Bean
    public AjaxRedirectFilter ajaxRedirectFilter() {
        return new AjaxRedirectFilter();
    }
}
