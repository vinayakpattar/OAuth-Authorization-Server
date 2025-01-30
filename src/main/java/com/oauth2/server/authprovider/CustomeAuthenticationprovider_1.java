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
public class CustomeAuthenticationprovider_1 implements AuthenticationProvider {

    @Autowired
    private UserDetailServ userDetailServ;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        String password = token.getCredentials().toString(); // retrieve the password
        System.out.println("username : " + username);
        System.out.println("password : " + password);

        // do something here
        UserDetails userDetails = userDetailServ.loadUserByUsername(username);
        System.out.println("userDetails :: " + userDetails);
        if(null != userDetails){
            System.out.println("User found");
            System.out.println("input User : " + username);
            System.out.println("input pass : " + password);
            System.out.println("user :"+ userDetails.getUsername());
            System.out.println("pass :"+ userDetails.getPassword());
            if(passwordEncoder.matches(password, userDetails.getPassword())){
                System.out.println("Password correct");
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            } else {
                System.out.println("password incorrect");
                throw new BadCredentialsException("Incorrect password");
            }
        }
        System.out.println("No user found");
        throw new BadCredentialsException("Error here");
//        return new UsernamePasswordAuthenticationToken(username, password, null);

        // if ok then return the authentication
//        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
