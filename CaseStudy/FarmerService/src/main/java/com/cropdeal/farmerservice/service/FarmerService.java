package com.cropdeal.farmerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cropdeal.farmerservice.exception.FarmerNotFoundException;
import com.cropdeal.farmerservice.model.Farmer;

@Service
public interface FarmerService {

	public List<Farmer> findAll();

	public Farmer getFarmerById(String id) throws FarmerNotFoundException;

	public Farmer addFarmer(Farmer farmer);

	public Farmer updateFarmer(Farmer farmer);

	public String deleteById(String farmerId);
}
