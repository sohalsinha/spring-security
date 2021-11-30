package com.frankmoley.security.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.ArrayList;

@SpringBootApplication
@EnableOAuth2Client
public class GuestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestAppApplication.class, args);
	}

	private static final String AUTH_TOKEN_URL="/oauth/token";

	@Value("${landon.guest.service.url}")
	private String serviceUrl;

	@Bean
	public OAuth2RestTemplate restTemplate(){
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(serviceUrl + AUTH_TOKEN_URL);
		resource.setClientId("guest_app");
		resource.setClientSecret("secret");
		resource.setGrantType("client_credentials");
		resource.setScope(new ArrayList<String>(){{add("READ_ALL_GUESTS");add("WRITE_GUEST");add("UPDATE_GUEST");}});
		resource.setAuthenticationScheme(AuthenticationScheme.form);
		AccessTokenRequest request = new DefaultAccessTokenRequest();

		return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(request));
	}
}
