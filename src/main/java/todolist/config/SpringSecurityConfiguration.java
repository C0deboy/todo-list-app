package todolist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import todolist.security.AuthenticationSuccessHandlerImpl;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@ComponentScan("todolist.services")
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Resource(name = "authService")
  private UserDetailsService userDetailsService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().authorizeRequests().antMatchers("/api/**").permitAll();
    http.authorizeRequests()
        .antMatchers("/", "/signup", "/forgotPassword", "/resetPassword", "/resources/**", "/oauth/token**").permitAll();
    http.authorizeRequests().antMatchers("/{username}/**").access("hasAnyAuthority({'USER', 'ADMIN'}) and #username == principal.username");
    http.authorizeRequests().antMatchers("/**").hasAuthority("ADMIN");

    http.formLogin()
        .loginPage("/login")
        .successHandler(authenticationSuccessHandler())
        .failureForwardUrl("/loginFailure").permitAll();

    http.rememberMe().key("uniqueAndSecret");

    http.logout().logoutSuccessUrl("/logoutSuccess").deleteCookies("JSESSIONID").permitAll();

    http.exceptionHandling().accessDeniedPage("/accessDenied");

    http.csrf().disable();
  }

  @Bean
  public AuthenticationSuccessHandlerImpl authenticationSuccessHandler() {
    return new AuthenticationSuccessHandlerImpl();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }



  @Bean
  public AuthenticationManager customAuthenticationManager() throws Exception {
    return authenticationManager();
  }
}
