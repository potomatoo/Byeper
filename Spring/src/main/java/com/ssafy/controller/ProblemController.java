package com.ssafy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.model.response.BasicResponse;
import com.ssafy.model.service.ProblemService;

@Controller
public class ProblemController {
	@Autowired
	private ProblemService problemService;

	@GetMapping("/api/public/problem/list")
	public Object FindProblemByVideo(@RequestParam String videoId) {
		BasicResponse response = new BasicResponse();

		response.data = problemService.FindProblemByVideo(videoId);

		response.status = (response.data != null) ? true : false;
		if (response.status) {
			response.message = "문제 조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.message = "문제 조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
}
