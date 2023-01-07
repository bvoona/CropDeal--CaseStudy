package com.cropdeal.cartservice.service;

import java.util.List;
//import java.util.Optional;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.cartservice.exception.CartAlreadyExistsException;
import com.cropdeal.cartservice.exception.CartNotFoundException;
import com.cropdeal.cartservice.model.Cart;
import com.cropdeal.cartservice.repository.CartRepository;



@Service
public class CartServiceImpl implements CartService {
	Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
	@Autowired
	CartRepository cartRepository;

	@Override
	public Cart getCartById(int cartId) 
	{
		return cartRepository.findById(cartId);
	}

	@Override
	public Cart updateCart(int cartId, Cart cart) throws CartNotFoundException {
		if (cartRepository.existsById(cartId)) {
			Cart updatedCart = cartRepository.findById(cartId);
			updatedCart.setCartId(cart.getCartId());
			updatedCart.setItems(cart.getItems());
			updatedCart.setTotalPrice(cart.getTotalPrice());
			cartRepository.save(updatedCart);
			return updatedCart;

		}
		else
		{
			logger.warn("NO CART FOUND WITH ID "+cartId);
			throw new CartNotFoundException("NO CART FOUND WITH ID "+cartId);
		}
		

	}

	@Override
	public List<Cart> getallcarts()  {
		
		  List<Cart> carts = cartRepository.findAll(); if (carts.isEmpty()) { throw new
		  CartNotFoundException("NO CARTS EXISTS"); } else { return carts; }
		 
		
	}

	@Override
	public double cartTotal(Cart cart) {

		return cart.getTotalPrice();
	}

	@Override
	public Cart addCart(Cart cart) throws CartAlreadyExistsException {

		return cartRepository.save(cart);

	}

	@Override
	public void deleteCartById(int cartId) throws CartNotFoundException
	{
		if(cartRepository.existsById(cartId))
		{
	    cartRepository.deleteById(cartId);
	    }
		else
		{
			logger.warn("NO CART FOUND WITH ID " +cartId);
			throw new CartNotFoundException("NO CART EXISTS WITH ID "+cartId);
		}
}
}
