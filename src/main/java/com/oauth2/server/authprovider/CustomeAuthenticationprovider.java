package com.oauth2.server.authprovider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomeAuthenticationprovider implements AuthenticationProvider {

    @Autowired
    private UserDetailServ userDetailServ;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        String password = token.getCredentials().toString(); // retrieve the password

        // Check User exists and password
        UserDetails userDetails = userDetailServ.loadUserByUsername(username);
        System.out.println("userDetails :: " + userDetails);
        if(null != userDetails){
            if(passwordEncoder.matches(password, userDetails.getPassword())){
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            } else {
                throw new BadCredentialsException("Incorrect password");
            }
        }
        System.out.println("No user found");
        throw new BadCredentialsException("Error here");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
