package com.trip.music.service;

import java.util.List;

import com.trip.music.vo.PlaylistStringDTO;
import com.trip.music.vo.PlaylistVO;

public interface MusicService {
	void createPlaylist(PlaylistStringDTO playlistDTO);
    PlaylistVO getPlaylist(int playlistId);
    List<PlaylistVO> getAllPlaylists();
    void deletePlaylist(int playlistId);
    void addTrackToPlaylist(int playlistId, String trackId);
    void removeTrackFromPlaylist(int playlistId, String trackId);
}
