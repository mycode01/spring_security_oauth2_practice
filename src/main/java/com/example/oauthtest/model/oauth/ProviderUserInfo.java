package com.example.oauthtest.model.oauth;

import java.util.Map;
import lombok.Data;

@Data
public class ProviderUserInfo {

  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  public ProviderUserInfo() {
  }

  public ProviderUserInfo(Map<String, Object> attributes,
      String nameAttributeKey, String name,
      String email, String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static ProviderUserInfo of(String registrationId,
      String userNameAttributeName,
      Map<String, Object> attributes) {
    if ("naver".equals(registrationId)) {
      return ofNaver("id", attributes);
    }
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static ProviderUserInfo ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return new ProviderUserInfo(attributes, userNameAttributeName, (String) attributes.get("name"),
        (String) attributes.get("email"), (String) attributes.get("picture"));
  }

  private static ProviderUserInfo ofNaver(String userNameAttributeName,
      Map<String, Object> attributes) {
    var response = (Map<String, Object>) attributes.get("response");
    return new ProviderUserInfo(response, userNameAttributeName, (String) response.get("name"),
        (String) attributes.get("email"), (String) attributes.get("picture")); // 세션 저장시 버그있음
  }

}
