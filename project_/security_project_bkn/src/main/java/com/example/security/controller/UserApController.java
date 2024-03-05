package com.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.service.UserApService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/public/user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserApController {
    
    @Autowired
    private UserApService service;

    @GetMapping("/getAll")
    public ResponseEntity<Object> allUser(){
        return new ResponseEntity<Object>(service.getAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("getLog")
    public ResponseEntity<Object> getLogin(@RequestBody UserAuthRequest request) throws Exception {
       try{ 
            return new ResponseEntity<Object>(service.getRefrescLog(request), HttpStatus.ACCEPTED);
       } catch (Exception e) {
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/getSingle")
    public ResponseEntity<Object> single(@RequestHeader String email){
        return new ResponseEntity<Object>(service.getUser(email), HttpStatus.ACCEPTED);
    }
    
    @GetMapping("/getUser/{email}")
    public ResponseEntity<Object> getUser(@PathVariable String email, @RequestHeader("Authorization") String token) throws Exception {
        System.out.println("sono nel getUser di UserApController");
        try {
            return new ResponseEntity<Object>(service.getUserAp(email, token), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<Object>("Errore: ", HttpStatus.BAD_REQUEST);
        }
    }
                
    @GetMapping("/getRole/{email}")
    public ResponseEntity<Object> getRoleUser(@PathVariable String email, @RequestHeader("Authorization") String token) throws Exception {
        System.out.println("sonon nel getRole di UserApController");
        try{
            return new ResponseEntity<Object>(service.getRole(email, token), HttpStatus.ACCEPTED);
        } catch(Exception e){
            return new ResponseEntity<>("Errore: ", HttpStatus.BAD_REQUEST);
        }
    }
                 /*      METODO IMPLEMENTATO, PROBLEMI IN UserApService NEL SETTAGGIO RUOLO 
    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@NonNull @PathVariable String id, @RequestBody UserAp user ) throws Exception {
        try {
            return new ResponseEntity<Object>(service.modificaUser(id, user), HttpStatus.ACCEPTED);            
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
        }
    }               
            
    @GetMapping("/getRoles/{email}")
    public ResponseEntity<Object> getRoles(@RequestBody UserAuthRequest request) throws Exception {
        try{
            return new ResponseEntity<Object>(service.getRole(request.getEmail(), request.getToken()), HttpStatus.ACCEPTED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }                  */
            

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@NonNull @RequestParam String id) throws Exception {
        try {
            return new ResponseEntity<Object>(service.deleteUserAp(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
        }
    }   

    @DeleteMapping("/deleteAll")
    public void deleteAll() throws Exception {
        service.deleteAllUserAp();
    }
}