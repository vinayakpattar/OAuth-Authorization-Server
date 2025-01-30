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

//@Configuration
@EnableWebSecurity
public class SecurityConfig_1 {

//    @Autowired
//    UserDetailsService userDetailsService;

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

//        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.authenticationProvider(customeAuthenticationprovider);
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
//                .formLogin().loginPage("/signIn").permitAll();
                .formLogin(Customizer.withDefaults());

        return http.build();
    }






    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }*/

    /*public UserDetails userDetails (){
        UserDetails userDetails = userDetailsService.loadUserByUsername()
    }*/
}
