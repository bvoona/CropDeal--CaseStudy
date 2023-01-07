package com.cropdeal.dealerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.cropdeal.dealerservice.exception.DealerNotFoundException;

import com.cropdeal.dealerservice.model.Dealer;

@Service
public interface DealerService {

	public List<Dealer> findAll();

	public Dealer getDealerById(String dealerId) throws DealerNotFoundException;

	public Dealer addDealer(Dealer dealer);

	public Dealer updateDealer(Dealer dealer);

	public String deleteById(String dealerId);
}
