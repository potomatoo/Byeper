package com.ssafy.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.PlayinfoDto;
import com.ssafy.model.dto.Video;
import com.ssafy.model.repository.VideoRepository;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoRepository videoRepository;

	@Override
	public List<Video> SaveAllVideo(List<Video> videos) {
		// TODO Auto-generated method stub
		return videoRepository.saveAll(videos);
	}

	@Override
	public Video DetailVideo(String videoId) {
		// TODO Auto-generated method stub
		Optional<Video> video = videoRepository.findById(videoId);

		if (video.isPresent())
			return video.get();
		else
			return null;
	}

	@Override
	public List<PlayinfoDto> FindVideobyPlaylist(int playlistId) {
		// TODO Auto-generated method stub

		return videoRepository.findVideoByPlaylist(playlistId);
	}

}
