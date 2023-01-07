package com.cropdeal.dealerservice.controller;

import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.cropdeal.dealerservice.exception.DealerNotFoundException;
import com.cropdeal.dealerservice.filters.JwtUtil;
import com.cropdeal.dealerservice.model.AuthenticationRequest;
import com.cropdeal.dealerservice.model.AuthenticationResponse;
import com.cropdeal.dealerservice.model.Crop;
import com.cropdeal.dealerservice.model.Dealer;
import com.cropdeal.dealerservice.model.UserModel;
import com.cropdeal.dealerservice.repository.UserRepository;
import com.cropdeal.dealerservice.service.DealerService;
import com.cropdeal.dealerservice.service.MyUserDetailsService;

@RestController
@RequestMapping("/dealer")
public class DealerController {

	Logger log = LoggerFactory.getLogger(DealerController.class);

	@Autowired
	DealerService dealerservice;

	@Autowired
	private RestTemplate restTemp;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private UserRepository repo;

	@GetMapping("/finddealers")
	public List<Dealer> getdealer() {
		log.info("All dealers are viewed");
		return dealerservice.findAll();
	}

	@GetMapping("/getDealerById/{id}")
	public Dealer getdealerById(@PathVariable String id) throws DealerNotFoundException {
		log.info(" dealer based on id is viewed");
		return dealerservice.getDealerById(id);
	}

	@PostMapping("/adddealer")
	public Dealer adddealer(@RequestBody Dealer dealer) {
		log.info(" New dealer details are added");
		return dealerservice.addDealer(dealer);
	}

	@PutMapping("/updatedealer")
	public Dealer updatedealer(@RequestBody Dealer dealer) {
		log.info("dealer details are updated");
		return dealerservice.updateDealer(dealer);
	}

	@DeleteMapping("/deletedealer/{id}")
	public String deletedealer(@PathVariable String id) {
		log.info("Deleted dealer based on id");
		dealerservice.deleteById(id);
		return "farmer deleted having id " + id;
	}

	@GetMapping("/dealer/get/crop")
	public List<Object> getCropatDealer() {
		log.info(" crop details will be viewed by dealer");
		Object[] crop = restTemp.getForObject("http://CropService/crop/findcrops", Object[].class);
		return Arrays.asList(crop);
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
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
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
