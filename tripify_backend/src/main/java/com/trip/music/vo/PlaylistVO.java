package com.trip.music.vo;

import java.util.ArrayList;
import java.util.List;

public class PlaylistVO {
    private int playlistId;
    private Integer planPlaceId;
    private List<TrackVO> trackIds = new ArrayList<>(); // Spotify 트랙 ID 리스트

    
    // Getters and Setters
    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public Integer getPlanPlaceId() {
        return planPlaceId;
    }

    public void setPlanPlaceId(Integer planPlaceId) {
        this.planPlaceId = planPlaceId;
    }

    public List<TrackVO> getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(List<TrackVO> trackIds) {
        this.trackIds = trackIds;
    }

	@Override
	public String toString() {
		return "PlaylistVO [playlistId=" + playlistId + ", planPlaceId=" + planPlaceId + ", trackIds=" + trackIds + "]";
	}
    
    
}
