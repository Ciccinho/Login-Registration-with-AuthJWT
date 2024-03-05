package com.example.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.error_class.UserAlreadyRegisteredException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequiredArgsConstructor
public class AuthenticartionController {
    
    @Autowired
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) throws Exception {
       try{ 
            return new ResponseEntity<Object>(service.register(request), HttpStatus.ACCEPTED);
       } catch (UserAlreadyRegisteredException e){
            return new ResponseEntity<Object>("User already present", HttpStatus.BAD_REQUEST);
       } catch (Exception e){
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
       }
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request )throws Exception {
        try {
            return new ResponseEntity<Object>(service.authenticate(request), HttpStatus.ACCEPTED);
        } catch (UserAlreadyRegisteredException e) {
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e ){
            return new ResponseEntity<Object>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody AuthenticationRequest request) throws Exception {
        try{
            return new ResponseEntity<Object>(service.signout(request), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
        }
    }
    
}