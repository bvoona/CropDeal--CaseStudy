package com.cropdeal.farmerservice.model;

public class Location {

	private String street;
	private String village;
	private String city;
	private String state;
	private String pincode;

	public Location() {

	}

	public Location(String street, String village, String city, String state, String pincode) {
		super();
		this.street = street;
		this.village = village;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "Location [street=" + street + ", village=" + village + ", city=" + city + ", state=" + state
				+ ", pincode=" + pincode + "]";
	}

}
