package com.example.samuraitravel.form;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationRegisterForm {
	private Integer houseId;
	private Integer userId;
	private String chekinDate;
	private String chekoutDate;
	private Integer numberOfPeople;
	private Integer amount;
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getChekinDate() {
		return chekinDate;
	}
	public void setChekinDate(String chekinDate) {
		this.chekinDate = chekinDate;
	}
	public String getChekoutDate() {
		return chekoutDate;
	}
	public void setChekoutDate(String chekoutDate) {
		this.chekoutDate = chekoutDate;
	}
	public Integer getNumberOfPeople() {
		return numberOfPeople;
	}
	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
