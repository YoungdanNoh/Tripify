package com.trip.music.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trip.music.vo.PlaylistVO;
import com.trip.music.vo.TrackVO;

@Mapper
public interface MusicMapper {
	void insertPlaylist(PlaylistVO playlistVO);
    void insertTrack(TrackVO trackVO);
    PlaylistVO getPlaylist(int playlistId);
    List<PlaylistVO> getAllPlaylists();
    void deletePlaylist(int playlistId);
    void deleteTrack(TrackVO trackVO);
}
