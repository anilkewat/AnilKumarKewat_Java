package com.codingassignment.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class InstrumentBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String instrument;
	private int account;
	private String accountType;
	private double quantity;
	private double delta;
	
	/**
	 * @return the instrument
	 */
	public String getInstrument() {
		return instrument;
	}
	/**
	 * @param instrument the instrument to set
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	/**
	 * @return the account
	 */
	public int getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(int account) {
		this.account = account;
	}
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the delta
	 */
	public double getDelta() {
		return delta;
	}
	/**
	 * @param delta the delta to set
	 */
	public void setDelta(double delta) {
		this.delta = delta;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ""+this.getInstrument() + ", " + this.getAccount()
				+ ", " + this.getAccountType() + ", " + new BigDecimal(this.getQuantity()).toPlainString() + ", " + new BigDecimal(this.getDelta()).toPlainString();
	} 
}
