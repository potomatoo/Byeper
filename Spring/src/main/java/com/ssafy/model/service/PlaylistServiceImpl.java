package com.ssafy.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.PlaylistDto;
import com.ssafy.model.dto.PlaylistProgressDto;
import com.ssafy.model.repository.PlaylistRepository;

@Service
public class PlaylistServiceImpl implements PlaylistService {
	@Autowired
	private PlaylistRepository playlistRepository;

	@Override
	public List<PlaylistDto> findAllPlaylistTeacher(String userId, int start) {
		// TODO Auto-generated method stub
		int index = start * 9;

		return playlistRepository.findPlaylistTeacher(userId, index);
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

	@Override
	public List<PlaylistDto> findAllPlaylistManangement(String userId) {
		// TODO Auto-generated method stub
		return playlistRepository.findByManagement(userId);
	}

	@Override
	public List<PlaylistProgressDto> getPlaylistProgress(String userId) {
		// TODO Auto-generated method stub
		return playlistRepository.findProgressInfo(userId);
	}

	@Override
	public List<PlaylistProgressDto> getPlaylistProgressManage(String userId) {
		// TODO Auto-generated method stub
		return playlistRepository.findProgressManage(userId);
	}

	@Override
	public List<PlaylistDto> SearchPlaylist(String playlistTitle) {
		// TODO Auto-generated method stub

		String query = "%" + playlistTitle + "%";
		return playlistRepository.SearchPlaylist(query);
	}

}
