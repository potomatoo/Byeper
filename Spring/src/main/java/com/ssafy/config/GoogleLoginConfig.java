package com.ssafy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;

@Configuration
public class GoogleLoginConfig {

	@Value("${googlelogin.client}")
	public String GOOGLE_CLIENT_ID;
	@Value("${googlelogin.secret}")
	public String GOOGLE_SECRIT_ID;

	@Bean
	GoogleConnectionFactory googleConnectionFactory() {
		return new GoogleConnectionFactory(GOOGLE_CLIENT_ID, GOOGLE_SECRIT_ID);
	}

	@Bean
	OAuth2Parameters oAuth2Parameters() {
		OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
		oAuth2Parameters.setRedirectUri("postmessage");
		oAuth2Parameters.setScope(
				"https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email openid");
		return oAuth2Parameters;
	}

}
