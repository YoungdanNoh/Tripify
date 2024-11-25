package com.trip.music.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trip.music.vo.PlaylistStringDTO;
import com.trip.music.vo.PlaylistVO;
import com.trip.music.vo.TrackVO;

@Mapper
public interface MusicMapper {
	void insertPlaylist(PlaylistStringDTO playlistDTO);
    void insertTrack(int playlistId, String track);
    PlaylistVO getPlaylist(int playlistId);
    List<PlaylistVO> getAllPlaylists();
    void deletePlaylist(int playlistId);
    void deleteTrack(TrackVO trackVO);
	void insertTrack(TrackVO trackVO);
}
