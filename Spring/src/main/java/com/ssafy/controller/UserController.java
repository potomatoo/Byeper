package com.ssafy.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.model.dto.UserDto;
import com.ssafy.model.response.BasicResponse;
import com.ssafy.model.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Value("${youtubeapi}")
	public String YOUTUBE_API;

	@PostMapping("/api/public/user/signup")
	public Object signup(@RequestBody UserDto user) {
		BasicResponse response = new BasicResponse();

		if (!userService.CheckEmail(user.getUserId())) {
			response.status = false;
			response.message = "이미 등록된 이메일 입니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		UserDto result = userService.Signup(user);
		response.data = result;
		response.status = true;
		response.message = "회원 가입이 완료되었습니다.";

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/api/private/user/detail")
	public Object UserInof(@RequestHeader("Authorization") String jwtToken) {
		BasicResponse response = new BasicResponse();

		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);
		if (user == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		user = userService.FindUserDetail(user.getUserId());
		if (user == null) {
			response.status = false;
			response.message = "사용자 조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		user.setUserPassword("");
		response.data = user;
		response.status = true;
		response.message = "사용자 정보입니다.";

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/api/public/user/checkemail")
	public Object CheckEmamil(@RequestParam("email") String email) {
		BasicResponse response = new BasicResponse();

		response.status = userService.CheckEmail(email);

		if (response.status) {
			response.message = "사용 가능한 이메일 입니다.";
			response.data = true;
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.data = false;
			response.message = "중복된 이메일 입니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
	}

	@PutMapping("/api/private/user/changeusername")
	public Object ChangeUserName(@RequestHeader("Authorization") String jwtToken, @RequestBody UserDto user) {
		BasicResponse response = new BasicResponse();

		UserDto userinfo = (UserDto) redisTemplate.opsForValue().get(jwtToken);
		if (userinfo == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.data = userService.UpdateUserName(userinfo.getUserId(), user.getUserName());
		response.status = (response.data != null) ? true : false;

		if (response.status) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/api/private/user/registteacher")
	public Object RegistArtist(@RequestHeader("Authorization") String jwtToken) {
		BasicResponse response = new BasicResponse();

		UserDto user = (UserDto) redisTemplate.opsForValue().get(jwtToken);
		if (user == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (user.getUserType() == 3) {
			response.status = false;
			response.message = "이미 교사로 등록되어 있습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		String url = "https://www.googleapis.com/youtube/v3/channels";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8"))); // Response Header to
																								// UTF-8
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("part", "id")
				.queryParam("forUsername", user.getUserId().split("@")[0]).queryParam("key", YOUTUBE_API).build(false); // 자동으로
																														// encode해주는
																														// 것을
																														// 막기
																														// 위해
																														// false

		String youtubeResult = restTemplate
				.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class)
				.getBody();

		JsonParser parser = new JsonParser();
		JsonObject youtubeJson = parser.parse(youtubeResult).getAsJsonObject();
		JsonArray items = youtubeJson.getAsJsonArray("items");

		if (items.size() == 0) {
			response.status = false;
			response.message = "등록된 채널이 없습니다.";
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		response.data = userService.RegistTeacher(user.getUserId(),
				items.get(0).getAsJsonObject().get("id").getAsString());
		response.status = (response.data != null) ? true : false;

		if (response.status) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/public/user/userinfo")
	public Object UserInfoByUserId(@RequestParam("userId") String userId) {
		BasicResponse response = new BasicResponse();

		UserDto user = userService.FindUserDetail(userId);
		if (user == null) {
			response.status = false;
			response.message = "사용자 조회에 실패하였습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		user.setUserPassword("");
		response.data = user;
		response.status = true;
		response.message = "사용자 정보입니다.";

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
