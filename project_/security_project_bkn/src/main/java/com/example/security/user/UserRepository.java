package com.example.security.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserAp, String> {
    
    public Optional<UserAp> findByEmail(String email);
    public List<UserAp> findAll();
}
