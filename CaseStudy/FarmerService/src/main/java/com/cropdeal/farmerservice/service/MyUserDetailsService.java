package com.cropdeal.farmerservice.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cropdeal.farmerservice.model.UserModel;
import com.cropdeal.farmerservice.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserModel user = repo.findByUsername(username);
		if (user == null) {
			return null;
		}
		String name = user.getUsername();
		String pwd = user.getPassword();

		return new User(name, pwd, new ArrayList<>());

	}

}
