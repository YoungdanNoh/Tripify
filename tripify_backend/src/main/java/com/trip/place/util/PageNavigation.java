package com.trip.place.util;

public class PageNavigation {
	
	private boolean startRange; // 현재 페이지가 이전이 눌려지지 않는 범위의 페이지 체크
	private boolean endRange; // 현재 페이지가 다음이 눌려지지 않는 범위의 페이지 체크
	private int totalCount; // 총 게시글 갯수
	private int newCount; // 새글 갯수
	private int totalPageCount; // 총 페이지 갯수
	private int currentPage; // 현재 페이지 번호
	private int naviSize; // 네비게이션 사이즈
	private int countPerPage; // 페이지당 글 갯수
	private String navigator;
	private int[] page; //현재 네비게이션에 나타나야 하는 페이지들의 번호

	public boolean isStartRange() {
		return startRange;
	}

	public void setStartRange(boolean startRange) {
		this.startRange = startRange;
	}

	public boolean isEndRange() {
		return endRange;
	}

	public void setEndRange(boolean endRange) {
		this.endRange = endRange;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getNewCount() {
		return newCount;
	}

	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNaviSize() {
		return naviSize;
	}

	public void setNaviSize(int naviSize) {
		this.naviSize = naviSize;
	}

	public String getNavigator() {
		return navigator;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	
	public int[] getPage() {
		return page;
	}

	public void makeNavigator() {
		int startPage = (currentPage - 1) / naviSize * naviSize + 1; //네비게이션의 시작페이지
		int endPage = startPage + naviSize - 1; //네비게이션의 끝 페이지
		
		if(totalPageCount < endPage) {
			endPage = totalPageCount;
		}
			
		this.page = new int[endPage-startPage+1]; //현재 페이지에서 보여야 할 페이지 번호들을 저장한다.
		int idx = 0;
		for(int i=startPage;i<=endPage;i++) {
			this.page[idx++] = i;
		}
	}

}
