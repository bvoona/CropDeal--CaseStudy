package com.cropdeal.farmerservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cropdeal.farmerservice.exception.FarmerNotFoundException;
import com.cropdeal.farmerservice.model.Crop;
import com.cropdeal.farmerservice.model.Farmer;
import com.cropdeal.farmerservice.repository.FarmerRepository;

@Service

public class FarmerServiceImpl implements FarmerService {

	@Autowired
	private RestTemplate restTemplate;
	String url = "http://crop-service/crop";

	@Autowired
	FarmerRepository farmerrepository;

	@Override
	public List<Farmer> findAll() {
		return farmerrepository.findAll();
	}

	@Override
	public Farmer getFarmerById(String farmerId) throws FarmerNotFoundException {

		return farmerrepository.findById(farmerId)
				.orElseThrow(() -> new FarmerNotFoundException("Id:" + farmerId + "Not found"));
	}

	@Override
	public Farmer addFarmer(Farmer farmer) {
		return farmerrepository.insert(farmer);
	}

	@Override
	public Farmer updateFarmer(Farmer farmer) {
		return farmerrepository.save(farmer);
	}

	@Override
	public String deleteById(String farmerId) {
		farmerrepository.deleteById(farmerId);
		return null;
	}
}
