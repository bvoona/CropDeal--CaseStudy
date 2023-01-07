package com.cropdeal.farmerservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cropdeal.farmerservice.exception.FarmerNotFoundException;
import com.cropdeal.farmerservice.filters.JwtUtil;
import com.cropdeal.farmerservice.model.AuthenticationRequest;
import com.cropdeal.farmerservice.model.AuthenticationResponse;
import com.cropdeal.farmerservice.model.Crop;
import com.cropdeal.farmerservice.model.Farmer;
import com.cropdeal.farmerservice.model.UserModel;
import com.cropdeal.farmerservice.repository.UserRepository;
import com.cropdeal.farmerservice.service.FarmerService;
import com.cropdeal.farmerservice.service.MyUserDetailsService;

@RestController
@RequestMapping("/farmer")
public class FarmerController {

	Logger log = LoggerFactory.getLogger(FarmerController.class);

	@Autowired
	private RestTemplate restTemp;

	@Autowired
	FarmerService farmerservice;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private UserRepository repo;

	@GetMapping("/findfarmers")
	public List<Farmer> getfarmer() {
		log.info("All farmers are viewed");
		return farmerservice.findAll();
	}

	@GetMapping("/getFarmerById/{id}")
	public Farmer getfarmerById(@PathVariable String id) throws FarmerNotFoundException {
		log.info(" farmer based on id is viewed");
		return farmerservice.getFarmerById(id);
	}

	@PostMapping("/addfarmer")
	public Farmer addfarmer(@RequestBody Farmer farmer) {
		log.info(" New farmer details are added" + farmer.toString());
		return farmerservice.addFarmer(farmer);
	}

	@PutMapping("/updatefarmer")
	public Farmer updatefarmer(@RequestBody Farmer farmer) {
		log.info("farmer details are updated");
		return farmerservice.updateFarmer(farmer);
	}

	@DeleteMapping("/deletefarmer/{id}")
	public String deletefarmer(@PathVariable String id) {
		log.info("Deleted farmer based on id");
		farmerservice.deleteById(id);
		return "farmer deleted having id " + id;
	}

	// ---------------get crops-----------

	@GetMapping("/farmer/get/crop")
	public List<Object> getCropatFarmer() {
		log.info("farmer can have a look of the available crops");
		Object[] crop = restTemp.getForObject("http://CropService/crop/findcrops", Object[].class);
		return Arrays.asList(crop);
	}
	// -------------get crops by id----------------

	@GetMapping("/farmer/get/crop/{id}")
	public Crop getCropatFarmerbyID(@PathVariable("id") String id) {
		log.info("farmer can get a particular crop");
		Crop crop = restTemp.getForObject("http://CropService/crop/getCropById/" + id, Crop.class);
		return crop;
	}

	// -------------delete the crop by farmer---------------------

	@RequestMapping(value = "/farmer/delete/crop/{id}", method = RequestMethod.DELETE)
	public String deleteCropById(@PathVariable("id") String id) {
		log.info("farmer deleted the crop he want to remove ");
		return restTemp.exchange("http://CropService/crop/deletecrop/" + id, HttpMethod.DELETE, null, String.class)
				.getBody();
	}

	// -----------------------------adding crops by farmer---------------

	@RequestMapping(value = "/farmer/add/crop", method = RequestMethod.POST)
	public String addCropatFarmer(@RequestBody Crop crop) {
		log.info("farmer can add crops");
		HttpEntity<Crop> entity = new HttpEntity<Crop>(crop);
		return restTemp.exchange("http://CropService/crop/addcrop", HttpMethod.POST, entity, String.class).getBody();

	}

	// ---------------------update the crop by farmer-----------------------------

	@RequestMapping(value = "/farmer/update/crop/{id}", method = RequestMethod.PUT)
	public String updateCropatFarmer(@RequestBody Crop crop) {
		log.info("farmer can update the crop");
		HttpEntity<Crop> entity = new HttpEntity<Crop>(crop);
		return restTemp.exchange("http://CropService/crop/updatecrop/", HttpMethod.PUT, entity, String.class).getBody();

	}

	/*-----------------------------------security----------------------------------*/

	@PostMapping("/reg")
	private ResponseEntity<?> subscribe(@RequestBody AuthenticationRequest request) {

		String username = request.getUsername();
		String password = request.getPassword();

		UserModel model = new UserModel();
		model.setUsername(username);
		model.setPassword(password);

		try {
			repo.save(model);
		} catch (Exception e) {
			return ResponseEntity
					.ok(new AuthenticationResponse("Error while subsribing the user with username " + username));
		}
		return ResponseEntity.ok(new AuthenticationResponse("client subscribed with username " + username));
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}

