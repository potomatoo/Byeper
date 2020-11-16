package com.ssafy.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.PlayDto;
import com.ssafy.model.dto.PlayinfoDto;

@Repository
public interface PlayRepository extends JpaRepository<PlayDto, Integer> {
	List<PlayDto> findByPlaylistId(int playlistId);

	@Query(value = "select play_id, play_log, playlist_id, play_complete,play_note, a.video_id, video_title,video_description,video_img,video_max_img from ssafy.play as a , ssafy.summary_video as b where a.video_id = b.video_id and a.play_id=:play_id", nativeQuery = true)
	public PlayinfoDto findPlayinfodetail(@Param("play_id") int playId);

}
