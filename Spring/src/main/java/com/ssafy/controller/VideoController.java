package com.ssafy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.model.response.BasicResponse;
import com.ssafy.model.service.VideoService;

@Controller
public class VideoController {
	@Autowired
	private VideoService videoService;

	@GetMapping("/api/public/video/detail")
	public Object VideoDetail(@RequestParam String videoId) {
		BasicResponse response = new BasicResponse();

		response.data = videoService.DetailVideo(videoId);

		if (response.data != null) {
			response.status = true;
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/api/public/video/findplaylist")
	public Object VideoFindbyPlaylist(@RequestParam int playlistId) {
		BasicResponse response = new BasicResponse();

		response.data = videoService.FindVideobyPlaylist(playlistId);

		if (response.data != null) {
			response.status = true;
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	}

}
