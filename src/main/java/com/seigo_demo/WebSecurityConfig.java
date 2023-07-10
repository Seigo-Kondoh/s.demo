package com.seigo_demo;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    //    /homeへのアクセスは認可不要でそれ以外へのアクセスは必要という内容
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
           /*     h2-consoleへのアクセスを認証なしで見るためコード(※本番環境時には削除)
                本番時にはcsrfを有効にする*/
                .securityMatcher("/h2-console/")
                .csrf((csrf) -> csrf.disable())
                .headers((headers) -> headers.frameOptions((frame) -> frame.sameOrigin()))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .authorities("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}