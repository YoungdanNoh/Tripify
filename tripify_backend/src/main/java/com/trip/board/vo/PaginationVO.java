package com.trip.board.vo;

import lombok.Data;

@Data
public class PaginationVO {
    private int currentPage;  // 현재 페이지
    private int totalRecords; // 전체 게시물 수
    private int pageSize;     // 한 페이지당 게시물 수
    private int totalPages;   // 총 페이지 수
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public PaginationVO(int currentPage, int totalRecords, int pageSize, int totalPages) {
		super();
		this.currentPage = currentPage;
		this.totalRecords = totalRecords;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
	}
	public PaginationVO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
