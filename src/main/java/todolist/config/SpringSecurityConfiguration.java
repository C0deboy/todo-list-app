package todolist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import todolist.security.AuthenticationSuccessHandlerImpl;

import javax.annotation.Resource;

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
    http.authorizeRequests()
        .antMatchers("/", "/signup", "/forgotPassword", "/resetPassword", "/resources/**").permitAll();
    http.authorizeRequests().antMatchers("/{username}/**")
        .access("hasAnyAuthority({'USER', 'ADMIN'}) and #username == principal.username");
    http.authorizeRequests().antMatchers("/**").hasAuthority("ADMIN");

    http.formLogin()
        .loginPage("/login")
        .successHandler(authenticationSuccessHandler())
        .failureForwardUrl("/loginFailure").permitAll();

    http.rememberMe().key("uniqueAndSecret");

    http.logout().logoutSuccessUrl("/logoutSuccess").deleteCookies("JSESSIONID").permitAll();

    http.exceptionHandling().accessDeniedPage("/accessDenied");
  }

  @Bean
  public AuthenticationSuccessHandlerImpl authenticationSuccessHandler() {
    return new AuthenticationSuccessHandlerImpl();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
