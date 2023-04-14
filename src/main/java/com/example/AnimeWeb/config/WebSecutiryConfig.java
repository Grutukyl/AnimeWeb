package com.example.AnimeWeb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

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
                       .antMatchers("/login*" , "/static/**")
                       .permitAll()
                       .anyRequest()
                       .authenticated()
                       .and()
                       .formLogin()
                       .loginPage("/login")
                       .defaultSuccessUrl("/ggg", true)
                       .failureUrl("/login?logout")
                       .and()
                       .logout()
                       .logoutUrl("/logout")
                       .deleteCookies("JSESSIONID")
                       .clearAuthentication(true);
                       return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
