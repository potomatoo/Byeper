package com.ssafy.controller;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.model.dto.UserDto;
import com.ssafy.model.dto.Video;
import com.ssafy.model.response.BasicResponse;

@Controller
public class YoutubeController {

	@Value("${youtubeapi}")
	public String YOUTUBE_API;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@GetMapping("/api/public/videos")
	public Object GetYutubeVideos(@RequestHeader("Authorization") String jwtToken) {
		BasicResponse response = new BasicResponse();

		UserDto userinfo = (UserDto) redisTemplate.opsForValue().get(jwtToken);
		if (userinfo == null) {
			response.status = false;
			response.message = "잘못된 사용자 입니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (userinfo.getChannelId() == null || userinfo.getChannelId().equals("")) {
			response.status = false;
			response.message = "교사로 등록되지 않았습니다.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		String url = "https://www.googleapis.com/youtube/v3/search";
		String nextPageToken = "";
		JsonParser parser = new JsonParser();

		List<Video> videoList = new LinkedList<>();

		do {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("part", "snippet")
					.queryParam("channelId", userinfo.getChannelId()).queryParam("type", "video")
					.queryParam("order", "date").queryParam("pageToken", nextPageToken).queryParam("key", YOUTUBE_API)
					.build(false);

			String youtubeResult = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class)
					.getBody();

			JsonObject youtubeJson = parser.parse(youtubeResult).getAsJsonObject();

			if (youtubeJson.has("nextPageToken")) {
				nextPageToken = youtubeJson.get("nextPageToken").getAsString();
			} else {
				nextPageToken = null;
			}

			JsonArray items = youtubeJson.getAsJsonArray("items");

			for (int i = 0; i < items.size(); i++) {
				JsonObject item = items.get(i).getAsJsonObject();

				Video video = new Video();

				video.setVideoId(item.get("id").getAsJsonObject().get("videoId").getAsString());
				video.setVideoTitle(item.get("snippet").getAsJsonObject().get("title").getAsString());
				video.setVideoDescription(item.get("snippet").getAsJsonObject().get("description").getAsString());
				video.setVideoImg(item.get("snippet").getAsJsonObject().get("thumbnails").getAsJsonObject().get("high")
						.getAsJsonObject().get("url").getAsString());
				video.setUserId(userinfo.getUserId());

				videoList.add(video);
			}

		} while (nextPageToken != null);

		response.data = videoList;
		response.status = response.data != null ? true : false;
		response.message = "비디오 조회 결과입니다.";
		if (response.status) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
