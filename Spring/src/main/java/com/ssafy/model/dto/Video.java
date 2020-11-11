package com.ssafy.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video")
public class Video {
	@Id
	@Column(name = "video_id")
	private String videoId;

	@Column(name = "video_title")
	private String videoTitle;

	@Column(name = "video_description")
	private String videoDescription;

	@Column(name = "video_img")
	private String videoImg;

	@Column(name = "video_max_img")
	private int videoMaxImg;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "video_file")
	private String videoFile;

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public String getVideoImg() {
		return videoImg;
	}

	public void setVideoImg(String videoImg) {
		this.videoImg = videoImg;
	}

	public int getVideoMaxImg() {
		return videoMaxImg;
	}

	public void setVideoMaxImg(int videoMaxImg) {
		this.videoMaxImg = videoMaxImg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
	}

	@Override
	public String toString() {
		return "Video [videoId=" + videoId + ", videoTitle=" + videoTitle + ", videoDescription=" + videoDescription
				+ ", videoImg=" + videoImg + ", videoMaxImg=" + videoMaxImg + ", userId=" + userId + ", videoFile="
				+ videoFile + "]";
	}

}
