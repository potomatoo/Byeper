package com.ssafy.controller;

import java.net.URLDecoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.config.JwtProperties;
import com.ssafy.model.dto.UserDto;
import com.ssafy.model.response.BasicResponse;
import com.ssafy.model.service.UserService;

@Controller
public class GoogleLoginController {

	@Value("${googlelogin.client}")
	public String GOOGLE_CLIENT_ID;
	@Value("${googlelogin.secret}")
	public String GOOGLE_SECRIT_ID;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@PostMapping("/api/public/google/login")
	public Object googleLogin(@RequestBody String authToken, RedirectAttributes rediAttributes) {
		MultiValueMap<String, String> parma = new LinkedMultiValueMap<String, String>();

		parma.add("code", URLDecoder.decode(authToken.replaceAll("\"", "")));
		parma.add("client_id", GOOGLE_CLIENT_ID);
		parma.add("client_secret", GOOGLE_SECRIT_ID);
		parma.add("redirect_uri", "postmessage");
		parma.add("grant_type", "authorization_code");

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://accounts.google.com/o/oauth2/token";

		String obj = restTemplate.postForEntity(url, parma, String.class).getBody();

		JsonParser jsonParser = new JsonParser();
		JsonObject result = jsonParser.parse(obj).getAsJsonObject();

		String id_token = result.get("id_token").getAsString();
		String access_token = result.get("access_token").getAsString();

		DecodedJWT decodedJwt = JWT.decode(id_token);
		String email = decodedJwt.getClaim("email").asString();

		UserDto user = userService.GoogleLogin(email);

		if (user == null) {
			BasicResponse basicResponse = new BasicResponse();
			basicResponse.data = null;
			basicResponse.status = false;
			basicResponse.message = "이미 가입된 이메일 입니다.";

			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		user.setUserPassword("");
		String token = JwtProperties.TOKEN_PREFIX + JWT.create().withSubject(user.getUserId())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

		this.redisTemplate.opsForValue().set(token, user);

		user.setUserPassword(token);
		BasicResponse basicResponse = new BasicResponse();
		basicResponse.data = user;
		basicResponse.status = true;
		basicResponse.message = "로그인이 완료되었습니다.";

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
}
