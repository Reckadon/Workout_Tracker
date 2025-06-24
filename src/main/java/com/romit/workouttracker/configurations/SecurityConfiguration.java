package com.romit.workouttracker.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withDefaultPasswordEncoder().username("romit").password("testpp").roles("USER").build();
        UserDetails user2 = User.withDefaultPasswordEncoder().username("hrishi").password("testhh").roles("USER").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

}
