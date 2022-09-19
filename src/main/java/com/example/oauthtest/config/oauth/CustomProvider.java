package com.example.oauthtest.config.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomProvider {

  NAVER{
    public Builder getBuilder() {
      ClientRegistration.Builder builder = getBuilder("naver",
          ClientAuthenticationMethod.CLIENT_SECRET_POST, DEFAULT_LOGIN_REDIRECT_URL);
      builder.authorizationUri("https://nid.naver.com/oauth2.0/authorize");
      builder.tokenUri("https://nid.naver.com/oauth2.0/token");
      builder.userInfoUri("https://openapi.naver.com/v1/nid/me");
      builder.userNameAttributeName("id");
      builder.clientName("Naver");
      return builder;
    }
  };


  private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

  protected final ClientRegistration.Builder getBuilder(
      String registrationId, ClientAuthenticationMethod method, String redirectUri) {
    ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
    builder.clientAuthenticationMethod(method);
    builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
    builder.redirectUriTemplate(redirectUri);
    return builder;
  }
  public abstract ClientRegistration.Builder getBuilder();

}
