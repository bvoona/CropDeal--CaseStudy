package com.cropdeal.farmerservice.model;

public class BankDetails {

	private String accountNumber;
	private String bankName;
	private String iFSCCode;

	public BankDetails() {

	}

	public BankDetails(String accountNumber, String bankName, String iFSCCode) {
		super();
		this.accountNumber = accountNumber;
		this.bankName = bankName;
		this.iFSCCode = iFSCCode;
	}

	@Override
	public String toString() {
		return "BankDetails [accountNumber=" + accountNumber + ", bankName=" + bankName + ", iFSCCode=" + iFSCCode
				+ "]";
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setiFSCCode(String iFSCCode) {
		this.iFSCCode = iFSCCode;
	}

}
