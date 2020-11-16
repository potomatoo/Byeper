package com.ssafy.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "play")
public class PlayDto {
	@Id
	@Column(name = "play_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int playId;

	@Column(name = "play_log")
	private int playLog;

	@Column(name = "play_complete")
	private int playComplete;

	@Column(name = "playlist_id")
	private int playlistId;

	@Column(name = "video_id")
	private String videoId;

	@Column(name = "play_note")
	private String playNote;

	public int getPlayId() {
		return playId;
	}

	public void setPlayId(int playId) {
		this.playId = playId;
	}

	public int getPlayLog() {
		return playLog;
	}

	public void setPlayLog(int playLog) {
		this.playLog = playLog;
	}

	public int getPlayComplete() {
		return playComplete;
	}

	public void setPlayComplete(int playComplete) {
		this.playComplete = playComplete;
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getPlayNote() {
		return playNote;
	}

	public void setPlayNote(String playNote) {
		this.playNote = playNote;
	}

	@Override
	public String toString() {
		return "PlayDto [playId=" + playId + ", playLog=" + playLog + ", playComplete=" + playComplete + ", playlistId="
				+ playlistId + ", videoId=" + videoId + "]";
	}

}
