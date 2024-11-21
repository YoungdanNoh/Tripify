package com.trip.place.vo;

import java.time.LocalDateTime;

public class PlaceCommentWithUserName {
    private Integer commentId;
    private Integer placeId;
    private Integer userId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public PlaceCommentWithUserName(Integer commentId, Integer placeId, Integer userId, String userName,
			String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.commentId = commentId;
		this.placeId = placeId;
		this.userId = userId;
		this.userName = userName;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public PlaceCommentWithUserName() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters and Setters
    
}
