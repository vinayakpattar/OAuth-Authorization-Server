package com.oauth2.server.web;

import com.oauth2.server.model.UserInfo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController

@org.springframework.stereotype.Controller
//@ControllerAdvice

public class Controller_1 {

    /*@CrossOrigin
    @RequestMapping(value = "/signIn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signIn( HttpServletResponse servletResponse, HttpServletRequest request) {
        System.out.println("Inside sign in: AuthenticationController" + OffsetDateTime.now());
        System.out.println("IP Address :: " + request.getRemoteAddr());
        //If application is in apllicatioin server/ behind Load Balancer

       *//* LoginResponseDto loginResponseDto = new LoginResponseDto();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        String username = null;
        try {
//            System.out.println("try begin + " + OffsetDateTime.now());
            Authentication authenti = authenticationManager.authenticate(token);

            username = authenti.getPrincipal().toString();
            System.out.println("authenticated username with Party ID :: " + username);

            //New here
            Session sessionId = authorizationService.createSessionId(username);
            ResponseCookie springCookie = null;
            if(null!=sessionId){

                springCookie = ResponseCookie.from(CookieSessionFilter.COOKIE_NAME, sessionId.getId())
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(120)
                        .build();

            }
            System.out.println("Before return + " + OffsetDateTime.now());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Allow-Credentials","true");
            headers.add("Access-Control-Allow-Headers","Accept, Content-Type");
            headers.add("Access-Control-Allow-Methods","GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS");
            loginResponseDto.setAuthenticated(true);
            loginResponseDto.setUsername(username);
            LoginResponse1 loginResponse1 = new LoginResponse1();


            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(gson.toJson(loginResponseDto));

        } catch(BadCredentialsException bce){
            System.out.println("BadCredentialsException here :: " + bce.getLocalizedMessage());
            loginResponseDto.setAuthenticated(false);
            loginResponseDto.setUsername(username);
            return ResponseEntity.ok().body(gson.toJson(loginResponseDto));


        } catch(UsernameNotFoundException un){
            System.out.println("UsernameNotFoundException here :: " + un.getLocalizedMessage());
            loginResponseDto.setAuthenticated(false);
            loginResponseDto.setUsername(username);
            return ResponseEntity.ok().body(gson.toJson(loginResponseDto));
//            return new ResponseEntity<>(un.getMessage(), HttpStatus.OK);
        } catch(AuthenticationException ae){
            System.out.println("AuthenticationException here :: " + ae.getLocalizedMessage());
            loginResponseDto.setAuthenticated(false);
            loginResponseDto.setUsername(username);
            return ResponseEntity.ok().body(gson.toJson(loginResponseDto));
        }*//*
        return ResponseEntity.ok().body("");
    }*/

//    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    @GetMapping("/signIn")
    public String signIn (){
        return "signIn";
    }

//    @RequestMapping(value = "/login_process", method = RequestMethod.POST)
    @PostMapping("/login_process")
    public String login_process (){
        System.out.println("loginProcess");
        return "signIn1";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String createUser(@ModelAttribute UserInfo userInfo, Model model) {
        System.out.println("first Name : {}" + userInfo);
        model.addAttribute("userInfo", new UserInfo());
        return "signIn1";

//        return ResponseEntity.ok().body(userInfo);
    }

}
