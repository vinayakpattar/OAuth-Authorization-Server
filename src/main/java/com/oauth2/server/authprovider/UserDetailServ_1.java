package com.oauth2.server.authprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServ_1 implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebClient webClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Username inside loadBYUSERNAME" + username);
        UserDetails userDetails = null;

        Flux<DigitalIdentity> dis = webClient
                .get()
//                .uri("http://192.168.3.41:4561/api/send")
//                .uri("http://localhost:9011/tmf-api/digitalIdentityManagement/v4/digitalIdentity?credential.login=vvp1&credential.password=vvp12123")
//                .uri("http://localhost:9011/tmf-api/digitalIdentityManagement/v4/digitalIdentity?credential.login="+username+"&credential.password="+encodedPassword+"")
                .uri("http://localhost:9011/tmf-api/digitalIdentityManagement/v4/digitalIdentity?credential.login="+username+"")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class) .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToFlux(DigitalIdentity.class);

        System.out.println("before block :: "+ OffsetDateTime.now());
        List<DigitalIdentity> digitalIdentities = dis.collectList().block();
        System.out.println("After block :: "+OffsetDateTime.now());
        System.out.println("List size :: " + digitalIdentities.size());


        if(digitalIdentities.size() == 0){
            throw new UsernameNotFoundException("Username : '"+username+"' not found");
//            throw new BadCredentialsException("No account found");
        } else if (digitalIdentities.size() > 1){

            throw new BadCredentialsException("More than One Account with username '" +username+"'");
        } else if (digitalIdentities.size() == 1){
            List<LoginPasswordCredential> dbLoginPasswordCred = digitalIdentities.get(0).getCredential().stream().filter(f -> f.getType().contains("LoginPasswordCredential"))
                    .map(x -> ((LoginPasswordCredential) x)).collect(Collectors.toList());
            String dbUsername = dbLoginPasswordCred.stream().filter(l-> l.getLogin().contains(username)).map(x-> x.getPassword()).collect(Collectors.joining (""));
            String dbPassword1 = dbLoginPasswordCred.stream().filter(l-> l.getLogin().contains(username)).map(x-> x.getPassword()).collect(Collectors.joining (""));
            System.out.println("dbPassword1 :: " + dbPassword1);

            userDetails = User.withUsername(username)
//                    .username("user")
                    .password(dbPassword1)
                    .roles("USER")
                    .build();
//            System.out.println("Password :: " + password);

//            boolean b = passwordEncoder.matches(password, dbPassword1);
//            System.out.println("b :: " + b);

            /*if(passwordEncoder.matches(password, dbPassword1)){
                System.out.println("Inside if");

                String usernameWithIndId = username+"##"+digitalIdentities.get(0).getIndividualIdentified().getId();
                String encodedUsername = "";
                try {
                    encodedUsername = URLEncoder.encode(usernameWithIndId, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(encodedUsername, password, new ArrayList<>());
                System.out.println("inside success :: "+OffsetDateTime.now());
                return token;
            } else {
                throw new BadCredentialsException("Error");
            }*/
        }



//            UserDetails
                    /*userDetails = User.withUsername("user")
//                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .roles("USER")
                    .build();*/

            return userDetails;

    }
}
