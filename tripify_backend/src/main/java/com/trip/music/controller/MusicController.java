package com.trip.music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.music.service.MusicService;
import com.trip.music.vo.PlaylistStringDTO;
import com.trip.music.vo.PlaylistVO;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:80"}, allowCredentials = "true")
@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @PostMapping("/playlist")
    public void createPlaylist(@RequestBody PlaylistStringDTO playlistDTO) {
    	System.out.println("controller:"+playlistDTO);
        musicService.createPlaylist(playlistDTO);
    }

    @GetMapping("/playlist/{planPlaceId}")
    public PlaylistVO getPlaylist(@PathVariable int planPlaceId) {
    	int playlistId = musicService.getPlaylistId(planPlaceId);
    	System.out.println("here is,.."+playlistId);
        return musicService.getPlaylist(playlistId);
    }

    @GetMapping("/playlists")
    public List<PlaylistVO> getAllPlaylists() {
        return musicService.getAllPlaylists();
    }

    @DeleteMapping("/playlist/{id}")
    public void deletePlaylist(@PathVariable int id) {
        musicService.deletePlaylist(id);
    }

    @PostMapping("/playlist/{id}/track")
    public void addTrackToPlaylist(@PathVariable int id, @RequestBody String trackId) {
        musicService.addTrackToPlaylist(id, trackId);
    }

    @DeleteMapping("/playlist/{id}/track")
    public void removeTrackFromPlaylist(@PathVariable int id, @RequestBody String trackId) {
        musicService.removeTrackFromPlaylist(id, trackId);
    }
}