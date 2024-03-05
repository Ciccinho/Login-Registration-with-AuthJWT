package com.example.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.config.JwtService;
import com.example.security.error_class.InvalidAuthTokenException;
import com.example.security.error_class.InvalidCredentialsException;
import com.example.security.error_class.UserAlreadyRegisteredException;
import com.example.security.user.Role;
import com.example.security.user.UserAp;
import com.example.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager autheManager;
    
    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyRegisteredException {
        var user = UserAp.builder().nome(request.getNome()).cognome(request.getCognome())
            .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
        if(repository.findByEmail(user.getEmail()).isPresent())             //gestione User già registrato
            throw new UserAlreadyRegisteredException("User già registrato");  
        repository.save(user);                                              //registrazione 
        var jwtToken = jwtService.generateToken(user);       
        return AuthenticationResponse.builder().token(jwtToken).build(); 
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws InvalidCredentialsException {
        try{
            autheManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = repository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
            var userPassCod = user.getPassword();
            if(passwordEncoder.matches(request.getPassword(),  userPassCod)){          //controllo password codificata
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).build();
            }else{
                throw new InvalidAuthTokenException("token scaduto o non valido");
            }
        } catch(BadCredentialsException e){
            throw new InvalidCredentialsException("Credenziali non corrette");
        }
    }

    public AuthenticationResponse signout(AuthenticationRequest request) throws Exception {
        try{
            autheManager.authenticate((new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())));
            var user = repository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
            var jwtToken = jwtService.generateLogoutToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        } catch(BadCredentialsException e){
            throw new InvalidCredentialsException("Credenziali non corrette (username o password)");
        }
    }
    
}