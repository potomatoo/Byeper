package com.ssafy.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "playlist")
public class PlaylistDto {
	@Id
	@Column(name = "playlist_id")
	private int playlistId;
	@Column(name = "playlist_title")
	private String playlistTitle;
	@Column(name = "playlist_description")
	private String playlistDescription;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "playlist_type")
	private int playlistType;
	@Column(name = "playlist_category")
	private int playlistCategory;
	@Column(name = "playlist_level")
	private int playlistLevel;
	@Column(name = "playlist_img")
	private String playlistImg;

	@Override
	public String toString() {
		return "PlaylistDto [playlistId=" + playlistId + ", playlistTitle=" + playlistTitle + ", playlistDescription="
				+ playlistDescription + ", userId=" + userId + ", playlistType=" + playlistType + ", playlistCategory="
				+ playlistCategory + ", playlistLevel=" + playlistLevel + ", playlistImg=" + playlistImg + "]";
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public String getPlaylistTitle() {
		return playlistTitle;
	}

	public void setPlaylistTitle(String playlistTitle) {
		this.playlistTitle = playlistTitle;
	}

	public String getPlaylistDescription() {
		return playlistDescription;
	}

	public void setPlaylistDescription(String playlistDescription) {
		this.playlistDescription = playlistDescription;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPlaylistType() {
		return playlistType;
	}

	public void setPlaylistType(int playlistType) {
		this.playlistType = playlistType;
	}

	public int getPlaylistCategory() {
		return playlistCategory;
	}

	public void setPlaylistCategory(int playlistCategory) {
		this.playlistCategory = playlistCategory;
	}

	public int getPlaylistLevel() {
		return playlistLevel;
	}

	public void setPlaylistLevel(int playlistLevel) {
		this.playlistLevel = playlistLevel;
	}

	public String getPlaylistImg() {
		return playlistImg;
	}

	public void setPlaylistImg(String playlistImg) {
		this.playlistImg = playlistImg;
	}

}
