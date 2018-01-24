package com.glaf.core.access.domain;

import java.io.Serializable;

public class AccessTotal implements Serializable{

	private static final long serialVersionUID = 1L;

	protected String userId;
	
	protected int day;
	
	protected int hour;
	
	protected int minute;
	
	protected int quantity;
	
	public AccessTotal(){
		
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getUserId() {
		return userId;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
