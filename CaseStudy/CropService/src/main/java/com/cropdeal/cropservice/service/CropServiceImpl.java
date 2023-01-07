package com.cropdeal.cropservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.cropservice.exception.CropNotFoundException;
import com.cropdeal.cropservice.model.Crop;
import com.cropdeal.cropservice.repository.CropRepository;

@Service
public class CropServiceImpl implements CropService {

	@Autowired
	CropRepository cropRepository;

	 @Autowired
	  private SequenceGeneratorService seqService;
	 
	@Override
	public List<Crop> findAll() {
		return cropRepository.findAll();
	}

	@Override
	public Crop getCropById(String id) throws CropNotFoundException {
		return cropRepository.findById(id).orElseThrow(() -> new CropNotFoundException("Id:" + id + "Not found"));

	}

	@Override
	public Crop addCrop(Crop crop) {
		return cropRepository.insert(crop);
	}

	@Override
	public Crop updateCrop(Crop crop) {
		return cropRepository.save(crop);
	}

	@Override
	public String deleteById(String id) {
		cropRepository.deleteById(id);
		return null;
	}
}
