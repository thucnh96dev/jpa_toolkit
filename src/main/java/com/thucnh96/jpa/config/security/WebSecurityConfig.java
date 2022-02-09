//package com.thucnh96.jpa.config.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
///**
// * @author : thucnh
// * @mailto : thucnh96.dev@gmail.com
// * @created :17/11/2021 - 9:39 AM
// */
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails user1 = User
//                .withUsername("thucnh")
//                .password("$2a$10$sWszOXuTlN0amQi8vXp4cerb.tJUQo.4FzLAnTCsSqChsYhlLdQWW") //codejava
//                .roles("USER")
//                .build();
//        UserDetails user2 = User
//                .withUsername("admin")
//                .password("$2a$10$PH0p2x2x8oi5bKx.80Bt7ubMAiHdZnqm9TC/Cpss9VoccyTYw1AoC") //nimda
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/**").permitAll()
//                .antMatchers("/generate").permitAll()
//                .antMatchers("/generate/**").permitAll()
//                .and()
//                .formLogin()
//                .permitAll()
//                .and()
//                .logout().permitAll()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/**").permitAll()
//                .antMatchers("/generate").permitAll();
//    }
//
//}
