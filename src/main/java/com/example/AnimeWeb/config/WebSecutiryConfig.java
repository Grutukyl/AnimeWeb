package com.example.AnimeWeb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity
public class WebSecutiryConfig {
    @Autowired
    private final JpaUserDetailsService jpaUserDetailsService;


    public WebSecutiryConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
               http.authorizeRequests()
                       .antMatchers("/rep/**").permitAll()
                       .antMatchers("/cadastrar","/static/**").permitAll()
                       .antMatchers("/registrar", "/static/**").permitAll()
                       .antMatchers("/login*" , "/static/**").permitAll()
                       .anyRequest()
                       .authenticated()
                       .and()
                       .formLogin()
                       .loginPage("/login")
                       .defaultSuccessUrl("/ggg", true)
                       .failureUrl("/login?error")
                       .and()
                       .logout()
                       .logoutUrl("/logout")
                       .deleteCookies("JSESSIONID")
                       .clearAuthentication(true)
                       .and()
                       .sessionManagement()
                       .invalidSessionUrl("/login?timeOut");
                       return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return  new HttpSessionEventPublisher();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
