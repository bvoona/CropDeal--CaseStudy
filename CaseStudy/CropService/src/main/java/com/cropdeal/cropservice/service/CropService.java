
package com.cropdeal.cropservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cropdeal.cropservice.exception.CropNotFoundException;
import com.cropdeal.cropservice.model.Crop;

@Service
public interface CropService {

	public List<Crop> findAll();

	public Crop getCropById(String id) throws CropNotFoundException;

	public Crop addCrop(Crop crop);

	public Crop updateCrop(Crop crop);

	public String deleteById(String id);
}
