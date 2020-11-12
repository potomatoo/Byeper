package com.ssafy.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.PlaylistDto;
import com.ssafy.model.repository.PlaylistRepository;

@Service
public class PlaylistServiceImpl implements PlaylistService {
	@Autowired
	private PlaylistRepository playlistRepository;

	@Override
	public List<PlaylistDto> findAllPlaylistTeacher(int start) {
		// TODO Auto-generated method stub
		int index = start * 10;

		return playlistRepository.findPlaylistTeacher(1, index);
	}

	@Override
	public List<PlaylistDto> findAllPlaylistUser(String userId) {
		// TODO Auto-generated method stub
		return playlistRepository.findbyUserid(userId);
	}

	@Override
	public PlaylistDto findPlaylistDetail(int playlistId) {
		// TODO Auto-generated method stub

		Optional<PlaylistDto> playlist = playlistRepository.findById(playlistId);

		if (playlist.isPresent())
			return playlist.get();
		else
			return null;
	}

	@Override
	public PlaylistDto savePlaylist(PlaylistDto playlist) {
		// TODO Auto-generated method stub
		return playlistRepository.save(playlist);
	}

	@Override
	public void deletePlaylist(int playlistId) {
		// TODO Auto-generated method stub

		playlistRepository.deleteById(playlistId);

	}

	@Override
	public PlaylistDto updatePlaylist(PlaylistDto update) {
		// TODO Auto-generated method stub

		return playlistRepository.save(update);
	}

}
