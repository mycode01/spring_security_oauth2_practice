package com.example.oauthtest;

import com.example.oauthtest.model.oauth.ProviderUserInfo;
import com.example.oauthtest.model.session.SessionUserInfo;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DefaultController {

  private final HttpSession httpSession;
  @RequestMapping("/")
  public String login(Model model) {

    return "login";
  }
  @RequestMapping("/logged-in")
  public String loggedIn(Model model){
    var info = (SessionUserInfo) httpSession.getAttribute("user");
    log.debug(info.toString());

    model.addAttribute("name", info.getName());
    return "loggedIn";
  }

  @RequestMapping("/test")
  public String test(Model model) {
    model.addAttribute("teststr", "테스트123123");
    return "testmvc";
  }

}
