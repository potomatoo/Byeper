package com.ssafy.model.dto;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "problem")
public class ProblemDto {

	@Id
	@Column(name = "problem_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int problemId;

	@Column(name = "problem_origin")
	private String problemOrigin;

	@Column(name = "problem_question")
	private String problemQuestion;

	@Column(name = "problem_charfield")
	private String problemCharfield;

	@Column(name = "video_id")
	private String videoId;

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public String getProblemOrigin() {
		return problemOrigin;
	}

	public void setProblemOrigin(String problemOrigin) {
		this.problemOrigin = problemOrigin;
	}

	public String getProblemQuestion() {
		return problemQuestion;
	}

	public void setProblemQuestion(String problemQuestion) {
		this.problemQuestion = problemQuestion;
	}

	public String getProblemCharfield() {
		return problemCharfield;
	}

	public void setProblemCharfield(String problemCharfield) {
		this.problemCharfield = problemCharfield;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	@Override
	public String toString() {
		return "ProblemDto [problemId=" + problemId + ", problemOrigin=" + problemOrigin + ", problemQuestion="
				+ problemQuestion + ", problemCharfield=" + problemCharfield + ", videoId=" + videoId + "]";
	}

}
