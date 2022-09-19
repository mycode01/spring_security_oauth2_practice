package com.example.oauthtest.model;

import com.example.oauthtest.model.oauth.ProviderUserInfo;
import com.example.oauthtest.persistence.User;
import com.example.oauthtest.persistence.User.Role;
import org.springframework.stereotype.Component;

@Component
public class OAuthInfoToUserConverter implements Converter<ProviderUserInfo, User> {

  @Override
  public User convert(ProviderUserInfo s) {
    return new User(s.getName(), s.getEmail(), s.getPicture(), Role.USER);
  }
}
