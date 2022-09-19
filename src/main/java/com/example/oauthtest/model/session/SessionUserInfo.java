package com.example.oauthtest.model.session;

import lombok.Data;

@Data
public class SessionUserInfo {
  private String name;
  private String email;
  private String picture;

  private SessionUserInfo() {
  }

  public SessionUserInfo(String name, String email, String picture) {
    this.name = name;
    this.email = email;
    this.picture = picture;
  }
}
