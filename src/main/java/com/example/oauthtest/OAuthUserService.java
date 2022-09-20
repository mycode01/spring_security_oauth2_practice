package com.example.oauthtest;

import com.example.oauthtest.model.Converter;
import com.example.oauthtest.model.OAuthInfoToUserConverter;
import com.example.oauthtest.model.oauth.ProviderUserInfo;
import com.example.oauthtest.model.session.SessionUserInfo;
import com.example.oauthtest.persistence.User;
import com.example.oauthtest.persistence.UserRepo;
import java.util.Collections;
import java.util.function.Function;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthUserService implements OAuth2UserService {

  private final HttpSession httpSession;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.debug(userRequest.toString());

    var oAuth2User = accessOAuth2User(userRequest);

    String providerId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    log.debug(oAuth2User.getName());

    ProviderUserInfo info = ProviderUserInfo.of(providerId, userNameAttributeName,
        oAuth2User.getAttributes());
    saveOrUpdate(info);
    httpSession.setAttribute("user", to(info));

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
        info.getAttributes(),
        info.getNameAttributeKey());
  }

  private OAuth2User accessOAuth2User(OAuth2UserRequest userRequest) {
    OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService(); // make bean
    return oAuth2UserService.loadUser(userRequest);
  }

  private final UserRepo userRepo;
  private final Converter<ProviderUserInfo, User> userConverter;
  private void saveOrUpdate(ProviderUserInfo info){
    var user = userRepo.findByEmail(info.getEmail())
        .map(e->e.update(info.getName(), info.getPicture()))
        .orElse(userConverter.convert(info));
    userRepo.save(user);
  }
  private SessionUserInfo to(ProviderUserInfo e){
    return new SessionUserInfo(e.getName(), e.getEmail(), e.getPicture());
  }
}
