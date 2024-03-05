package com.example.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.security.controller.UserAuthRequest;
import com.example.security.controller.UserAuthResponse;
import com.example.security.error_class.InvalidAuthTokenException;
import com.example.security.error_class.InvalidCredentialsException;
import com.example.security.user.UserAp;
import com.example.security.user.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;

import com.example.security.config.JwtService;

@Service
public class UserApService {
    
    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;

    public UserAp getUserAp(String email, String token) throws InvalidAuthTokenException {
        // System.out.println("sono in getUserAp dentro userApService");
         UserAp user = repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
         //if(jwtService.isTokenValid(token, user)){
            try{
                System.out.println("sono in getUserAp dentro userApService ");
                return user;
            }catch(ExpiredJwtException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Token non valido o scaduto", e);
            }
        // }else{
        //     throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Token non valido o scaduto");
        // }
    }

    public UserAp getUser(String email){
        System.out.println("sono in getUser dentro userApService");
        return repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User non trovato "+email));
    }

    public List<UserAp> getAll() {
        return repository.findAll();
    }

    public UserAuthResponse getRole(String email, String token) throws InvalidAuthTokenException {
        System.out.println("sono in getRole dentro userApService");
        UserAp user = repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
         //if(jwtService.isTokenValid(token, user)){
            try{
                System.out.println("ruolo dell'user: "+user.getAuthorities());
                return UserAuthResponse.builder().response((user.getAuthorities())).build();
            }catch(BadCredentialsException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Token non valido o scaduto ", e);
            }
        // }else{
        //     throw new ResponseStatusException(HttpStatus.FORBIDDEN,"token non valido o scaduto");
        // }
    }


    public UserAuthResponse getRefrescLog(UserAuthRequest request) throws InvalidAuthTokenException {
       try{
           var user = repository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found for this email: "));
           if(jwtService.extractUsername(request.getToken()) == user.getEmail()){
                if(jwtService.isTokenValid(request.getToken(), user)){
                    return UserAuthResponse.builder().response(request.getToken()).build();
                }else{
                    var jwtToken = jwtService.generateToken(user);
                    return UserAuthResponse.builder().response(jwtToken).build();
                }
            }else{
                throw new InvalidAuthTokenException("User non regiastrato");
            }
       }catch(Exception e){
            throw new InvalidAuthTokenException("User non registrato");
       }
    }

    
    /*                  METODO IMPLEMENTATO DA PROBLEMI SUL SETTAGGIO DEL RUOLO 
    public UserAp modificaUser(@NonNull String id, UserAp u) throws Exception {
        UserAp userAp = repository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found for this id: "+id));
        userAp.setNome(u.getNome());
        userAp.setCognome(u.getCognome());
        userAp.setEmail(u.getEmail());
        userAp.setPassword(u.getPassword());
        userAp.setRole(u.getRole());
        System.out.println("upgrade User "+id+" correct");
        return repository.save(userAp);
    }           */

    public UserAp deleteUserAp(String id) throws Exception {
        UserAp user = repository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found for this id: "+id));   
        repository.delete(user);
        return user;
    }

    public void deleteAllUserAp() {
        System.out.println("tutti gli User sono stati eliminati");
        repository.deleteAll();
    }
}