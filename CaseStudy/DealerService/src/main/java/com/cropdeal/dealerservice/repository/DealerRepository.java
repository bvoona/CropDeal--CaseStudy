package com.cropdeal.dealerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.dealerservice.model.Dealer;

@Repository
public interface DealerRepository extends MongoRepository<Dealer,String>{

}
