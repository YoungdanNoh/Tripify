package com.trip.music.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.music.mapper.MusicMapper;
import com.trip.music.vo.PlaylistVO;
import com.trip.music.vo.TrackVO;

@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicMapper musicMapper;

    @Override
    public void createPlaylist(PlaylistVO playlistVO) {
        musicMapper.insertPlaylist(playlistVO);
        for (TrackVO track : playlistVO.getTrackIds()) {
            musicMapper.insertTrack(track);
        }
    }

    @Override
    public PlaylistVO getPlaylist(int playlistId) {
    	PlaylistVO p = musicMapper.getPlaylist(playlistId);
    	System.out.println(p.toString());
        return p;
    }

    @Override
    public List<PlaylistVO> getAllPlaylists() {
        return musicMapper.getAllPlaylists();
    }

    @Override
    public void deletePlaylist(int playlistId) {
        musicMapper.deletePlaylist(playlistId);
    }

    @Override
    public void addTrackToPlaylist(int playlistId, String trackId) {
        musicMapper.insertTrack(new TrackVO(playlistId, trackId));
    }

    @Override
    public void removeTrackFromPlaylist(int playlistId, String trackId) {
        musicMapper.deleteTrack(new TrackVO(playlistId, trackId));
    }
}
