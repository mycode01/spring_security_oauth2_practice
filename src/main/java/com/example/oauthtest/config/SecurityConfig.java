package com.example.oauthtest.config;

import com.example.oauthtest.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
//        .antMatchers("/auth/**").permitAll()
//        .antMatchers("/v3/**").permitAll()
//        .antMatchers("/v1/**").permitAll()
//        .antMatchers("/swagger-ui/**").permitAll()
//        .antMatchers("/login/**").permitAll()
//        .antMatchers("/**").authenticated();
        .antMatchers("/").permitAll();

    http.headers().defaultsDisabled().contentTypeOptions();
    http.headers().frameOptions().disable().xssProtection().block(true);

    http.formLogin().disable()
        .logout().disable()
        .cors().and().csrf().disable()
        .oauth2Login()
        .userInfoEndpoint().userService(oAuthUserService)
        .and()
        .defaultSuccessUrl("/logged-in");
  }


  private final OAuthUserService oAuthUserService;
}
