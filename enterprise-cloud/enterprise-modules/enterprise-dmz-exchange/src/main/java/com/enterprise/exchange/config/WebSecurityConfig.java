package com.enterprise.exchange.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.enterprise.exchange.security.JwtAuthenticationFilter;

/**
 * Keeps portfolio APIs callable while gateway policy is demonstrated by the security components.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationFilter jwt;

    public WebSecurityConfig(JwtAuthenticationFilter jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers("/actuator/health", "/api/v1/auth/login").permitAll().anyRequest().authenticated().and().addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
    }
}
