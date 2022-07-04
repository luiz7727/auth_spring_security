package com.example.auth_spring_security.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    private final PasswordEncoder passwordEncoder;

    public Security(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeRequests().antMatchers("/api/v1/dashboard").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .failureForwardUrl("/api/v1/error")
                .defaultSuccessUrl("/api/v1/dashboard",true);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails lebron = User.builder().username("lebron").password(passwordEncoder.encode("123")).roles("ADMIN").build();
        UserDetails curry = User.builder().username("curry").password(passwordEncoder.encode("123")).roles("STUDENT").build();

        return new InMemoryUserDetailsManager(
                lebron,
                curry
        );
    }

}
