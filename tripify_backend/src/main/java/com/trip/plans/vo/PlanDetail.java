package com.trip.plans.vo;

public class PlanDetail {
	int plan_id;
	String title;
	String location;
	String start_date;
	String end_date;
	String img;
	int plan_place_id;
	String visit_date;
	String place_name;
	String description;
	int order_in_day;
	
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getVisit_date() {
		return visit_date;
	}
	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}
	public String getPlace_name() {
		return place_name;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getOrder_in_day() {
		return order_in_day;
	}
	public void setOrder_in_day(int order_in_day) {
		this.order_in_day = order_in_day;
	}
	public int getPlan_place_id() {
		return plan_place_id;
	}
	public void setPlan_place_id(int plan_place_id) {
		this.plan_place_id = plan_place_id;
	}
	
}
