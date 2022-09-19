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
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static ProviderUserInfo ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return new ProviderUserInfo(attributes, userNameAttributeName, (String)attributes.get("name"),
        (String)attributes.get("email"), (String)attributes.get("picture"));
  }

}
