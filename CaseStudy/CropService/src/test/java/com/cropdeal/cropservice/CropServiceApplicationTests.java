package com.cropdeal.cropservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cropdeal.cropservice.service.CropServiceImpl;
import com.cropdeal.cropservice.exception.CropNotFoundException;
import com.cropdeal.cropservice.model.Crop;
import com.cropdeal.cropservice.repository.CropRepository;

@SpringBootTest
class CropServiceApplicationTests {

	@Autowired
	private CropServiceImpl cropcontroller;

	@MockBean
	private CropRepository croprepo;

	@Test
	void addcropTest() {
		Crop crop = new Crop("111", "abcc", "organic", "5kg", null, "500","sri");
		when(croprepo.insert(crop)).thenReturn(crop);
		Crop res = cropcontroller.addCrop(crop);
		assertEquals(crop.getId(), res.getId());
	}

	@Test
	void getCropTest() {
		when(croprepo.findAll()).thenReturn(Stream.of(new Crop("123", "abcd", "organic", "3kg", null, "700","tanu"),
				new Crop("321", "cba", "organic", "6kg", null, "600","hari")).collect(Collectors.toList()));
		assertEquals(2, cropcontroller.findAll().size());
	}

	@Test
	void deleteCropbyidTest() {
		String crop = Crop("999", "def", "def@33", "234567", null, null,"vni");
		cropcontroller.deleteById("999");
		verify(croprepo, times(1)).deleteById("999");
	}

	private String Crop(String string, String string2, String string3, String string4, Object object, Object object2,
			String string5) {
			return null;
	}


	@Test
	void getCropByIdTest(){
		try {
			String id = "2";
			Crop crop = cropcontroller.getCropById(id);
			if(null != crop) {
				verify(croprepo).findById(crop.getId());
			}
		}
		catch (CropNotFoundException e) {
			System.out.println("exception occured"+ e);
		}
		
	}

}
