package com.ssafy.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.model.dto.ProblemDto;

@Service
public interface ProblemService {

	public List<ProblemDto> FindProblemByVideo(String videoId);

	public List<ProblemDto> SaveAllProblem(List<ProblemDto> problems);
}
