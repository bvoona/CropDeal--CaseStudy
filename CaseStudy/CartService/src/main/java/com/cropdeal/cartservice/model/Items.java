package com.cropdeal.cartservice.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Items
{
	private String id;
	


private String cropName;
@NotEmpty
@Min(0)
private double price;
@Min(1)
private int quantity;
private String image;
public Items() {
	
}
public Items(String id, String productName, double price, int quantity, String image) {
	super();
	this.id = id;
	this.cropName = productName;
	this.price = price;
	this.quantity = quantity;
	this.image = image;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getProductName() {
	return cropName;
}
public void setProductName(String productName) {
	this.cropName = productName;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}


}
