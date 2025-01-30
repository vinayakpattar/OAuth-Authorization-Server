package com.oauth2.server.authprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserDetailServ implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebClient webClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Username inside loadBYUSERNAME" + username);
        UserDetails userDetails = null;

        /*
        Get USER Details from any of the sources
        Create UserDetails Object and return
         */



            return userDetails;

    }
}
