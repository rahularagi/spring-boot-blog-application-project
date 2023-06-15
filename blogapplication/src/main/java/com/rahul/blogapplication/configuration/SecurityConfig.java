package com.rahul.blogapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
//        http.authorizeHttpRequests(configurer ->
//                configurer
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/view_comments/{postId}","/addUser","/view_post/{postId}","/new_comment/{postId}").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form ->
//                        form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/authenticateTheUser").defaultSuccessUrl("/home")
//                                .permitAll()
//                )
//                .logout(logout->logout.permitAll()
//                );
//        return http.build();
      return http.csrf(csrf -> csrf.disable()).cors(cors->cors.disable())
               .authorizeHttpRequests()
               .requestMatchers("/","/home","/view_comments","/addUser","/view_post/{postId}","/new_comment").permitAll()
                // .anyRequest().permitAll()
              // .requestMatchers("/api/**","/api/posts").permitAll()
//               .and()
//               .authorizeHttpRequests().requestMatchers("").authenticated()
              .anyRequest().authenticated()
               .and().formLogin(form ->
                        form
                                .loginPage("/login").permitAll()
                                .loginProcessingUrl("/authenticateTheUser").defaultSuccessUrl("/home").permitAll()
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
