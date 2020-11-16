package com.ssafy.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.PlayDto;
import com.ssafy.model.dto.PlayinfoDto;
import com.ssafy.model.repository.PlayRepository;

@Service
public class PlayServiceImpl implements PlayService {

	@Autowired
	private PlayRepository playRepository;

	@Override
	public List<PlayDto> SaveAllPlay(List<PlayDto> plays) {
		// TODO Auto-generated method stub
		return playRepository.saveAll(plays);
	}

	@Override
	public PlayDto updatePlay(PlayDto play) {
		// TODO Auto-generated method stub
		return playRepository.save(play);
	}

	@Override
	public List<PlayDto> FindPlayByPlaylist(int playlistid) {
		// TODO Auto-generated method stub
		return playRepository.findByPlaylistId(playlistid);
	}

	@Override
	public PlayinfoDto FindPalyinfoDetail(int playId) {
		// TODO Auto-generated method stub
		return playRepository.findPlayinfodetail(playId);
	}

}
