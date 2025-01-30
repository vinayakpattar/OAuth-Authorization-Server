package com.oauth2.server.conf;

import com.oauth2.server.authprovider.CustomeAuthenticationprovider;
import com.oauth2.server.authprovider.UserDetailServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomeAuthenticationprovider customeAuthenticationprovider;

    @Autowired
    UserDetailServ userDetailServ;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return authentication -> authentication;
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServ)
                .passwordEncoder(passwordEncoder);

        auth.authenticationProvider(customeAuthenticationprovider);
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
