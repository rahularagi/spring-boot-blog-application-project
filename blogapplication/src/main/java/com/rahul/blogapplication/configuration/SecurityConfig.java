package com.rahul.blogapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoUserDetailsService();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests()
                .requestMatchers("/", "/home", "/view_comments", "/addUser", "/view_post/{postId}", "/new_comment").permitAll()
                .requestMatchers("/api/posts","/api/posts/{postId}","/api/pagination","/api/comment","/api/comment/{postId}","/api/comment/new/{postId}").permitAll()
                .requestMatchers(HttpMethod.POST, "/posts/new").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin(form ->
                        form
                                .loginPage("/login").permitAll()
                                .loginProcessingUrl("/authenticateTheUser").defaultSuccessUrl("/home").permitAll()
                ).logout(logout ->
                        logout
                                .permitAll()
                )
                .build();
    }
    @Bean
   public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   @Bean
   public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
   }
}
