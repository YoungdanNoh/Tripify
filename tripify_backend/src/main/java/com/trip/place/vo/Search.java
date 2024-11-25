package com.trip.place.vo;

public class Search {

	String pgno; //페이지 넘버
	String sido; //시도
	String gugun; //구군
	String type; //어트랙션 종류
	String word; //검색어
	Integer userId; // 기본 null 
	
	public String getPgno() {
		return pgno;
	}
	public void setPgno(String pgno) {
		this.pgno = pgno;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
