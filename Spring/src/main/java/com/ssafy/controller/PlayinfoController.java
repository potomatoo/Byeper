package com.ssafy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.model.dto.PlayDto;
import com.ssafy.model.dto.PlayinfoDto;
import com.ssafy.model.dto.PlayinfoDtoImpl;
import com.ssafy.model.response.BasicResponse;
import com.ssafy.model.service.PlayService;

@Controller
public class PlayinfoController {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private PlayService playService;

	@PutMapping("/api/private/play/update")
	public Object UpdatePaly(@RequestHeader("Authorization") String jwtToken, @RequestBody PlayinfoDtoImpl playinfo) {
		BasicResponse response = new BasicResponse();

		PlayDto play = new PlayDto();

		play.setPlayId(playinfo.getPlay_id());
		play.setPlayLog(playinfo.getPlay_log());
		play.setPlayComplete(playinfo.getPlay_complete());
		play.setPlaylistId(playinfo.getPlaylist_id());
		play.setVideoId(playinfo.getVideo_id());
		play.setPlayNote(playinfo.getPlay_note());

		response.data = playService.updatePlay(play);

		if (response.data != null) {
			response.status = true;
			response.message = "수정에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "수정에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/public/play/detail")
	public Object UpdatePaly(@RequestParam int playId) {
		BasicResponse response = new BasicResponse();

		response.data = playService.FindPalyinfoDetail(playId);

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
