package com.example.oauthtest.model.session;

import java.io.Serializable;
import lombok.Data;

@Data
public class SessionUserInfo implements Serializable {
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
