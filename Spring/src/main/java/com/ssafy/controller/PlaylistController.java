package com.ssafy.controller;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.model.dto.PlayDto;
import com.ssafy.model.dto.PlaylistDto;
import com.ssafy.model.dto.PlaylistProgressDto;
import com.ssafy.model.dto.UserDto;
import com.ssafy.model.dto.Video;
import com.ssafy.model.response.BasicResponse;
import com.ssafy.model.service.PlayService;
import com.ssafy.model.service.PlaylistService;
import com.ssafy.model.service.VideoService;

@Controller
public class PlaylistController {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private PlaylistService playlistService;
	@Autowired
	private PlayService playService;
	@Autowired
	private VideoService videoService;

	private String[] allowFile = { "jpg", "png", "JPG", "PNG" };

	@GetMapping("/api/public/playlist/teacher")
	public Object FindAllPlaylist(@RequestHeader("Authorization") String jwtToken, @RequestParam int start) {
		BasicResponse response = new BasicResponse();

		String userId = "";
		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);
		if (user != null) {
			userId = user.getUserId();
		}
		response.data = playlistService.findAllPlaylistTeacher(userId, start);

		if (response.data != null) {
			response.status = true;
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/public/playlist/management")
	public Object FindAllPlaylistManagement(@RequestHeader("Authorization") String jwtToken) {
		BasicResponse response = new BasicResponse();

		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);
		if (user == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.data = playlistService.findAllPlaylistManangement(user.getUserId());

		if (response.data != null) {
			response.status = true;
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/public/playlist/user")
	public Object FindAllPlaylist(@RequestHeader("Authorization") String jwtToken) {
		BasicResponse response = new BasicResponse();
		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);

		response.data = playlistService.findAllPlaylistUser(user.getUserId());

		if (response.data != null) {
			response.status = true;
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/public/playlist/detail")
	public Object FindDetailPlaylist(@RequestParam int playlistId) {
		BasicResponse response = new BasicResponse();

		response.data = playlistService.findPlaylistDetail(playlistId);

		if (response.data != null) {
			response.status = true;
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/api/private/playlist/save")
	public Object Saveplaylist(@RequestHeader("Authorization") String jwtToken, @RequestBody PlaylistDto playlist) {
		BasicResponse response = new BasicResponse();
		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);
		if (user == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		playlist.setUserId(user.getUserId());

		response.data = playlistService.savePlaylist(playlist);

		if (response.data != null) {
			response.status = true;
			response.message = "저장에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "저장에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/api/public/playlist/delete")
	public Object DeletePlaylist(@RequestHeader("Authorization") String jwtToken, @RequestParam int playlistId) {
		BasicResponse response = new BasicResponse();

		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);
		PlaylistDto playlist = playlistService.findPlaylistDetail(playlistId);

		if (user.getUserId() != playlist.getUserId()) {
			response.status = false;
			response.message = "사용자가 일치하지 않습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		playlistService.deletePlaylist(playlistId);

		response.status = true;
		response.message = "삭제에 성공하였습니다.";
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping("/api/public/playlist/update")
	public Object UpdatePlaylist(@RequestHeader("Authorization") String jwtToken, @RequestBody PlaylistDto playlist) {
		BasicResponse response = new BasicResponse();
		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);

		if (user.getUserId() != playlist.getUserId()) {
			response.status = false;
			response.message = "사용자가 일치하지 않습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		response.data = playlistService.updatePlaylist(playlist);

		if (response.data != null) {
			response.status = true;
			response.message = "저장에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "저장에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/api/public/playlist/imgupload")
	public Object UploadFile(@RequestPart("file") MultipartFile file) {
		BasicResponse response = new BasicResponse();

		String fileName = UUID.randomUUID().toString();
		String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

		boolean allow = false;

		for (int i = 0; i < allowFile.length; i++) {
			if (fileExtension.equals(allowFile[i]))
				allow = true;
		}

		if (!allow) {
			response.status = false;
			response.message = "허용되지 않은 파일 확장자 입니다.(사용가능 확장자 png,jpg)";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		String path = fileName + '.' + fileExtension;
		try {
			file.transferTo(new File("/var/file/playlist/" + path));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.status = false;
			response.message = "파일 업로드에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.data = path;
		response.status = (response.data != null) ? true : false;
		if (response.status) {
			response.message = "썸네일 등록에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.message = "썸네일 등록에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/api/pirvate/playlist/addvideo", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object AddVideo(@RequestHeader("Authorization") String jwtToken, @RequestParam int playlistId,
			@RequestBody List<Video> videos) {
		BasicResponse response = new BasicResponse();

		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);

		List<PlayDto> plays = new LinkedList<PlayDto>();

		for (int i = 0; i < videos.size(); i++) {
			PlayDto play = new PlayDto();

			play.setPlaylistId(playlistId);
			play.setVideoId(videos.get(i).getVideoId());
			play.setPlayLog(0);
			play.setPlayComplete(0);

			plays.add(play);
		}

		List<Video> videoResult = videoService.SaveAllVideo(videos);
		List<PlayDto> playResult = playService.SaveAllPlay(plays);

		response.data = videoResult;
		response.status = (response.data != null) ? true : false;
		if (response.status) {
			response.message = "비디오 등록에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.message = "비디오 등록에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/private/playlist/subscribe")
	public Object SubscribePlaylist(@RequestHeader("Authorization") String jwtToken, @RequestParam int playlistId) {
		BasicResponse response = new BasicResponse();

		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);

		if (user == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		PlaylistDto playlist = playlistService.findPlaylistDetail(playlistId);

		PlaylistDto newPlayList = new PlaylistDto();

		newPlayList.setPlaylistCategory(playlist.getPlaylistCategory());
		newPlayList.setPlaylistDescription(playlist.getPlaylistDescription());
		newPlayList.setPlaylistImg(playlist.getPlaylistImg());
		newPlayList.setPlaylistLevel(playlist.getPlaylistLevel());
		newPlayList.setPlaylistTitle(playlist.getPlaylistTitle());
		newPlayList.setPlaylistType(playlist.getPlaylistId());
		newPlayList.setUserId(user.getUserId());

		playlist = playlistService.savePlaylist(newPlayList);

		if (playlist == null) {
			response.status = false;
			response.message = "플레이 리스트 생성에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		List<PlayDto> plays = playService.FindPlayByPlaylist(playlistId);

		List<PlayDto> subscribePlays = new LinkedList<>();

		for (int i = 0; i < plays.size(); i++) {
			PlayDto subscribePlay = new PlayDto();

			subscribePlay.setPlayComplete(0);
			subscribePlay.setPlaylistId(newPlayList.getPlaylistId());
			subscribePlay.setPlayLog(plays.get(i).getPlayLog());
			subscribePlay.setPlayNote("");
			subscribePlay.setVideoId(plays.get(i).getVideoId());

			subscribePlays.add(subscribePlay);
		}

		playService.SaveAllPlay(subscribePlays);

		response.data = playlist;
		response.status = (response.data != null) ? true : false;
		if (response.status) {
			response.message = "강의등록에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.message = "강의등록에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/public/playlist/progress")
	public Object playlistProgress(@RequestHeader("Authorization") String jwtToken) {
		BasicResponse response = new BasicResponse();

		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);

		if (user == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		List<PlaylistProgressDto> progress = playlistService.getPlaylistProgress(user.getUserId());

		Map<Integer, Map<String, Integer>> progressmap = new HashMap<Integer, Map<String, Integer>>();

		int total = 0;
		int complete = 0;

		for (int i = 0; i < progress.size(); i++) {
			Map<String, Integer> progressinfo = new HashMap<String, Integer>();

			complete += progress.get(i).getcomplete();
			total += progress.get(i).gettotal();

			progressinfo.put("complete", progress.get(i).getcomplete());
			progressinfo.put("total", progress.get(i).gettotal());

			progressmap.put(progress.get(i).getplaylist_id(), progressinfo);
		}

		Map<String, Integer> totalInfo = new HashMap<String, Integer>();

		totalInfo.put("complete", complete);
		totalInfo.put("total", total);

		progressmap.put(0, totalInfo);

		response.data = progressmap;
		response.status = (response.data != null) ? true : false;
		if (response.status) {
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/public/playlist/progressmanage")
	public Object playlistProgressManage(@RequestHeader("Authorization") String jwtToken) {
		BasicResponse response = new BasicResponse();

		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);

		if (user == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		List<PlaylistProgressDto> progress = playlistService.getPlaylistProgressManage(user.getUserId());

		Map<Integer, Map<String, Integer>> progressmap = new HashMap<Integer, Map<String, Integer>>();

		int total = 0;
		int complete = 0;

		for (int i = 0; i < progress.size(); i++) {
			Map<String, Integer> progressinfo = new HashMap<String, Integer>();

			complete += progress.get(i).getcomplete();
			total += progress.get(i).gettotal();

			progressinfo.put("complete", progress.get(i).getcomplete());
			progressinfo.put("total", progress.get(i).gettotal());

			progressmap.put(progress.get(i).getplaylist_id(), progressinfo);
		}

		Map<String, Integer> totalInfo = new HashMap<String, Integer>();

		totalInfo.put("complete", complete);
		totalInfo.put("total", total);

		progressmap.put(0, totalInfo);

		response.data = progressmap;
		response.status = (response.data != null) ? true : false;
		if (response.status) {
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/public/playlist/search")
	public Object SearchPlaylist(@RequestParam String playlistTitle) {
		BasicResponse response = new BasicResponse();

		response.data = playlistService.SearchPlaylist(playlistTitle);
		if (response.data != null) {
			response.status = true;
			response.message = "조회에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.status = false;
			response.message = "조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
