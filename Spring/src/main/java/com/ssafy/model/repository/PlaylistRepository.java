package com.ssafy.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.PlaylistDto;
import com.ssafy.model.dto.PlaylistProgressDto;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistDto, Integer> {
	@Query(value = "select * from ssafy.playlist where playlist_type = 0 and playlist_id not in (select playlist_type from playlist where user_id = :user_id and playlist_type !=0) limit :start ,9", nativeQuery = true)
	public List<PlaylistDto> findPlaylistTeacher(@Param("user_id") String user_id, @Param("start") Integer start);

	@Query(value = "select * from ssafy.playlist where user_id = :user_id and playlist_type !=0", nativeQuery = true)
	public List<PlaylistDto> findbyUserid(@Param("user_id") String userId);

	@Query(value = "select * from ssafy.playlist where user_id =:user_id and playlist_type = 0", nativeQuery = true)
	public List<PlaylistDto> findByManagement(@Param("user_id") String userId);

	@Query(value = "select playlist_id,sum(if(play_complete=1,1,0)) as complete ,count(*)  as total from ssafy.play group by playlist_id having playlist_id in (select playlist_id from ssafy.playlist where user_id = :user_id and playlist_type!=0)", nativeQuery = true)
	public List<PlaylistProgressDto> findProgressInfo(@Param("user_id") String userId);

	@Query(value = "select playlist_type as playlist_id, sum(if(complete=total,1,0))as complete, count(*) as total from (select playlist_id,sum(if(play_complete=1,1,0)) as complete ,count(*) as total,(select playlist_type from ssafy.playlist where playlist_id=a.playlist_id)  as playlist_type from ssafy.play as a group by playlist_id having playlist_id in (select playlist_id  from ssafy.playlist where playlist_type in (select playlist_id from ssafy.playlist where user_id = :user_id and playlist_type =0))) as b group by playlist_type", nativeQuery = true)
	public List<PlaylistProgressDto> findProgressManage(@Param("user_id") String userId);

	@Query(value = "select * from ssafy.playlist where playlist_title like :palylist_title and playlist_type =0", nativeQuery = true)
	public List<PlaylistDto> SearchPlaylist(@Param("palylist_title") String playlist_title);

}
