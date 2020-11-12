package com.ssafy.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.model.dto.PlaylistDto;

@Service
public interface PlaylistService {
	public List<PlaylistDto> findAllPlaylistTeacher(int start);

	public List<PlaylistDto> findAllPlaylistUser(String userId);

	public PlaylistDto findPlaylistDetail(int playlistId);

	public PlaylistDto savePlaylist(PlaylistDto playlist);

	public void deletePlaylist(int playlistId);

	public PlaylistDto updatePlaylist(PlaylistDto update);
}
