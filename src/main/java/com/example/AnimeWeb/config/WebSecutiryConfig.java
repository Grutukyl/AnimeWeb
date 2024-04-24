package com.example.AnimeWeb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/rep/**").permitAll()
                        .requestMatchers("/favoritos").permitAll()
                        .requestMatchers(HttpMethod.POST,"/favoritos").permitAll()
                        .requestMatchers("/cadastrar","/static/**").permitAll()
                        .requestMatchers("/registrar", "/static/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/listagem", true)
                ).logout((logout) -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                ).sessionManagement((sessionManagement) -> sessionManagement
                        .invalidSessionUrl("/login?timeOut")
                ).csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/favoritos")
                );
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
