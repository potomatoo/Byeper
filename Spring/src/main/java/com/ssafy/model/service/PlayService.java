package com.ssafy.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.PlayDto;
import com.ssafy.model.dto.PlayinfoDto;
import com.ssafy.model.repository.PlayRepository;

@Service
public interface PlayService {
	public List<PlayDto> SaveAllPlay(List<PlayDto> plays);

	public PlayDto updatePlay(PlayDto play);

	public List<PlayDto> FindPlayByPlaylist(int plylistid);

	public PlayinfoDto FindPalyinfoDetail(int playId);
}
