package com.ssafy.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.PlaylistDto;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistDto, Integer> {
	@Query(value = "select * from ssafy.playlist where playlist_type = :playlist_type limit :start ,10", nativeQuery = true)
	public List<PlaylistDto> findPlaylistTeacher(@Param("playlist_type") int playlistType,
			@Param("start") Integer start);

	@Query(value = "select * from ssafy.playlist where user_id = :user_id", nativeQuery = true)
	public List<PlaylistDto> findbyUserid(@Param("user_id") String userId);
}
 