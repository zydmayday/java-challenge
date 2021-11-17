package jp.co.axa.apidemo.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

  /**
   * So we allow any user with SCOPE_USER and SCOPE_ADMIN to access GET endpoints. And any user with
   * SCOPE_ADMIN can manipulate data. For other static resources, we allow all users to access.
   *
   * @param http
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/v1/employees/**")
        .hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
        .antMatchers(HttpMethod.POST, "/api/v1/employees/**")
        .hasAuthority("SCOPE_ADMIN")
        .antMatchers(HttpMethod.DELETE, "/api/v1/employees/**")
        .hasAuthority("SCOPE_ADMIN")
        .antMatchers(HttpMethod.PUT, "/api/v1/employees/**")
        .hasAuthority("SCOPE_ADMIN")
        .anyRequest()
        .permitAll()
        .and()
        .oauth2ResourceServer()
        .jwt();
  }
}
