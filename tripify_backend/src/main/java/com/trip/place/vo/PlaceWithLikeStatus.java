package com.trip.place.vo;

public class PlaceWithLikeStatus {

    private int placeId;
    private String title;
    private String areaCode;
    private String siGunGuCode;
    private String contentTypeId;
    private String addr1;
    private String addr2;
    private double latitude;
    private double longitude;
    private String firstImage1;
    private int likeCount;
    private String likeStatus; // 좋아요 상태 (true / false)
    
	public PlaceWithLikeStatus(int placeId, String title, String areaCode, String siGunGuCode,
			String contentTypeId, String addr1, String addr2, double latitude, double longitude, String firstImage1,
			int likeCount, String likeStatus) {
		super();
		this.placeId = placeId;
		this.title = title;
		this.areaCode = areaCode;
		this.siGunGuCode = siGunGuCode;
		this.contentTypeId = contentTypeId;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.latitude = latitude;
		this.longitude = longitude;
		this.firstImage1 = firstImage1;
		this.likeCount = likeCount;
		this.likeStatus = likeStatus;
	}

	public PlaceWithLikeStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSiGunGuCode() {
		return siGunGuCode;
	}

	public void setSiGunGuCode(String siGunGuCode) {
		this.siGunGuCode = siGunGuCode;
	}

	public String getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getFirstImage1() {
		return firstImage1;
	}

	public void setFirstImage1(String firstImage1) {
		this.firstImage1 = firstImage1;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(String likeStatus) {
		this.likeStatus = likeStatus;
	}

	@Override
	public String toString() {
		return "PlaceWithLikeStatus [placeId=" + placeId + ", title=" + title + ", areaCode=" + areaCode
				+ ", siGunGuCode=" + siGunGuCode + ", contentTypeId=" + contentTypeId + ", addr1=" + addr1 + ", addr2="
				+ addr2 + ", latitude=" + latitude + ", longitude=" + longitude + ", firstImage1=" + firstImage1
				+ ", likeCount=" + likeCount + ", likeStatus=" + likeStatus + "]";
	}
    
    
}