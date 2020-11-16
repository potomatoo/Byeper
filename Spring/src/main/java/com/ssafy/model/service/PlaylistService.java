package com.ssafy.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.model.dto.PlaylistDto;
import com.ssafy.model.dto.PlaylistProgressDto;

@Service
public interface PlaylistService {
	public List<PlaylistDto> findAllPlaylistTeacher(String userId, int start);

	public List<PlaylistDto> findAllPlaylistManangement(String userId);

	public List<PlaylistDto> findAllPlaylistUser(String userId);

	public PlaylistDto findPlaylistDetail(int playlistId);

	public PlaylistDto savePlaylist(PlaylistDto playlist);

	public void deletePlaylist(int playlistId);

	public PlaylistDto updatePlaylist(PlaylistDto update);

	public List<PlaylistProgressDto> getPlaylistProgress(String userId);

	public List<PlaylistProgressDto> getPlaylistProgressManage(String userId);

	public List<PlaylistDto> SearchPlaylist(String playlistTitle);
}
