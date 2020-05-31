package com.example.WEB.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@SuppressWarnings("deprecation")
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username,password,enabled FROM users WHERE username=?")
        		.authoritiesByUsernameQuery("SELECT username, role FROM users WHERE username=?");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().antMatchers("/register", "/login", "/test").permitAll()
    		.antMatchers("/products/**").hasAnyRole("ADMIN","USER")
    		.antMatchers("/admin/**").hasRole("ADMIN")
    		.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout();
    	
    	
    	/*
        http.authorizeRequests().antMatchers("/", "/register","/accessdenied","/showevents", "/test").permitAll().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
                .permitAll();
        http.exceptionHandling().accessDeniedPage("/accessdenied");
        */
    }
    
}
