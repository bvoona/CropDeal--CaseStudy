package com.cropdeal.cropservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.cropservice.model.Crop;
@Repository
public interface CropRepository extends MongoRepository<Crop ,String> {

}
