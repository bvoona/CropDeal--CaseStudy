package com.cropdeal.cropservice.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.cropservice.exception.CropNotFoundException;
import com.cropdeal.cropservice.model.Crop;
import com.cropdeal.cropservice.service.CropService;
import com.cropdeal.cropservice.service.CropServiceImpl;

@RestController
@RequestMapping("/crop")
public class CropController {

	Logger log = LoggerFactory.getLogger(CropController.class);

	@Autowired
	CropService cropservice;

	@GetMapping("/findcrops")
	public List<Crop> getcrop() {
		log.info("All available crops are viewed");
		return cropservice.findAll();
	}

	@GetMapping("/getCropById/{id}")
	public Crop getcropById(@PathVariable String id) throws CropNotFoundException {
		log.info(" crop based on id is viewed");
		return cropservice.getCropById(id);
	}

	@PostMapping("/addcrop")
	public Crop addcrop(@RequestBody Crop crop) {
		log.info(" New crop details are added");
		return cropservice.addCrop(crop);
	}

	@PutMapping("/updatecrop")
	public Crop updateCrop(@RequestBody Crop crop) {
		log.info("crop details are updated");
		return cropservice.updateCrop(crop);
	}

	@DeleteMapping("/deletecrop/{id}")
	public String deletecrop(@PathVariable String id) {
		log.info("Deleted crop based on id");
		cropservice.deleteById(id);
		return "crop deleted having id " + id;
	}
}
