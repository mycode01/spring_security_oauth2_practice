package com.example.oauthtest.config;

import com.example.oauthtest.config.oauth.CustomProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class CustomProviderConfig {

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository(
      OAuth2ClientProperties oAuth2ClientProperties,
//      @Value("${custom.oauth2.naver.client-id}") String kakaoClientId,
//      @Value("${custom.oauth2.naver.client-secret}") String kakaoClientSecret,
      @Value("${spring.security.oauth2.client.registration.naver.client-id}") String naverClientId,
      @Value("${spring.security.oauth2.client.registration.naver.client-secret}") String naverClientSecret,
      @Value("${spring.security.oauth2.client.registration.naver.scope}") String[] naverScope) {
    List<ClientRegistration> registrations = oAuth2ClientProperties
        .getRegistration().keySet().stream()
        .map(client -> getRegistration(oAuth2ClientProperties, client))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    return new InMemoryClientRegistrationRepository(registrations);
  }

  public ClientRegistration getRegistration(OAuth2ClientProperties clientProperties,
      String client) {
    OAuth2ClientProperties.Registration registration = clientProperties.getRegistration()
        .get(client);
    switch (client) {
      case "google":
        return CommonOAuth2Provider.GOOGLE.getBuilder(client)
            .clientId(registration.getClientId())
            .clientSecret(registration.getClientSecret())
            .scope("email", "profile")
            .build();
      case "facebook":
        return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
            .clientId(registration.getClientId())
            .clientSecret(registration.getClientSecret())
            .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
            .scope("email")
            .build();
      case "naver":
        return CustomProvider.NAVER.getBuilder().clientId(registration.getClientId())
            .clientSecret(registration.getClientSecret())
            .scope(registration.getScope())
            .jwkSetUri("temp").build();
      case "kakao":
      default:
        return null;
    } // security 에 포함된 oauth 프로바이더가 enum으로 만들어져 extends 도 불가능하고 동적으로 선택도 못함
  }


}
