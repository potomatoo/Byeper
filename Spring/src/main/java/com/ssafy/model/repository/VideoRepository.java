package com.ssafy.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.model.dto.PlayinfoDto;
import com.ssafy.model.dto.Video;

public interface VideoRepository extends JpaRepository<Video, String> {
	@Query(value = "select play_id, play_log, playlist_id, play_complete,play_note, a.video_id, video_title,video_description,video_img,video_max_img from ssafy.play as a , ssafy.summary_video as b where a.video_id = b.video_id and a.playlist_id=:playlist_id", nativeQuery = true)
	public List<PlayinfoDto> findVideoByPlaylist(@Param("playlist_id") int playlistId);

}
