package com.cropdeal.dealerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.dealerservice.model.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

	UserModel findByUsername(String username);

}
