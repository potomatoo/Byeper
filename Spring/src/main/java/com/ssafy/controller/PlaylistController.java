package com.ssafy.controller;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
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

import com.ssafy.model.dto.PlaylistDto;
import com.ssafy.model.dto.UserDto;
import com.ssafy.model.response.BasicResponse;
import com.ssafy.model.service.PlaylistService;

import io.swagger.models.Response;

@Controller
public class PlaylistController {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private PlaylistService playlistService;

	private String[] allowFile = { "jpg", "png", "JPG", "PNG" };
	
	@GetMapping("/api/public/palylist/teacher")
	public Object FindAllPlaylist(@RequestParam int start) {
		BasicResponse response = new BasicResponse();

		response.data = playlistService.findAllPlaylistTeacher(start);

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

	@GetMapping("/api/public/palylist/user")
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

	@GetMapping("/api/public/palylist/detail")
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
			response.message = "그림 등록에 성공하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.message = "그림 등록에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
