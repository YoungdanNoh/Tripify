package com.trip.music.vo;

public class TrackVO {
    private int playlistId;
    private String spotifyTrackId;

	// Getters and Setters
    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getSpotifyTrackId() {
        return spotifyTrackId;
    }

    public void setSpotifyTrackId(String spotifyTrackId) {
        this.spotifyTrackId = spotifyTrackId;
    }
    
 // 문자열을 받아 spotifyTrackId로 설정하는 생성자 추가
    public TrackVO(String spotifyTrackId) {
        this.spotifyTrackId = spotifyTrackId;
    }

	public TrackVO(int playlistId, String spotifyTrackId) {
		super();
		this.playlistId = playlistId;
		this.spotifyTrackId = spotifyTrackId;
	}

	public TrackVO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return spotifyTrackId;
	}
    
}
