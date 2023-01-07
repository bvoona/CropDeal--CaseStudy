package com.cropdeal.dealerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.dealerservice.exception.DealerNotFoundException;
import com.cropdeal.dealerservice.model.Dealer;
import com.cropdeal.dealerservice.repository.DealerRepository;

@Service
public class DealerServiceImpl implements DealerService {

	@Autowired
	DealerRepository dealerrepository;

	@Override
	public List<Dealer> findAll() {
		return dealerrepository.findAll();
	}

	@Override
	public Dealer getDealerById(String dealerId) throws DealerNotFoundException {
		return dealerrepository.findById(dealerId)
				.orElseThrow(() -> new DealerNotFoundException("Id:" + dealerId + "Not found"));

	}

	@Override
	public Dealer addDealer(Dealer dealer) {
		return dealerrepository.insert(dealer);
	}

	@Override
	public Dealer updateDealer(Dealer dealer) {
		return dealerrepository.save(dealer);
	}

	@Override
	public String deleteById(String dealerId) {
		dealerrepository.deleteById(dealerId);
		return null;
	}

}
