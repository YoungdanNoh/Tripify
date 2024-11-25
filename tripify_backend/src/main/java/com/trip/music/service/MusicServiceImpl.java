package com.trip.music.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.music.mapper.MusicMapper;
import com.trip.music.vo.PlaylistStringDTO;
import com.trip.music.vo.PlaylistVO;
import com.trip.music.vo.TrackVO;

@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicMapper musicMapper;

    @Transactional
    @Override
    public void createPlaylist(PlaylistStringDTO playlistDTO) {
        musicMapper.insertPlaylist(playlistDTO);
        for (String track : playlistDTO.getTrackIds()) {
            musicMapper.insertTrack(playlistDTO.getPlaylistId(), track);
        }
    }

    @Override
    public PlaylistVO getPlaylist(int playlistId) {
    	PlaylistVO p = musicMapper.getPlaylist(playlistId);
    	System.out.println(p.toString());
        return p;
    }
    
    @Override
    public int getPlaylistId(int planPlaceId) {
    	int p = musicMapper.getPlaylistId(planPlaceId);
    	System.out.println(p);
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
