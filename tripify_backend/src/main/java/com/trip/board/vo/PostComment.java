package com.trip.board.vo;

import java.time.LocalDateTime;

public class PostComment {
	private Integer commentId;
	private Integer postId;
	private Integer userId;
	private String content;
	private String userName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public PostComment(Integer commentId, Integer postId, Integer userId, String content, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.commentId = commentId;
		this.postId = postId;
		this.userId = userId;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public PostComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
