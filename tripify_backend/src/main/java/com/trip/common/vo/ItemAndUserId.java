package com.trip.common.vo;

public class ItemAndUserId {
	int userId;
	int placeId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPlaceId() {
		return placeId;
	}
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public ItemAndUserId(int userId, int placeId) {
		super();
		this.userId = userId;
		this.placeId = placeId;
	}
	public ItemAndUserId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
