package com.cropdeal.cartservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="cart")
public class Cart 
{

@Id	
private int cartId;

private double totalPrice;
private List<Items> items;

public Cart() {

}

public Cart(int cartId, double totalPrice, List<Items> items) {
	super();
	this.cartId = cartId;
	this.totalPrice = totalPrice;
	this.items = items;
}

public int getCartId() {
	return cartId;
}

public void setCartId(int cartId) {
	this.cartId = cartId;
}

public double getTotalPrice() {
	return totalPrice;
}

public void setTotalPrice(double totalPrice) {
	this.totalPrice = totalPrice;
}

public List<Items> getItems() {
	return items;
}

public void setItems(List<Items> items) {
	this.items = items;
}


 
}
