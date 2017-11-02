package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import todolist.entities.User;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserService userService;

  @Autowired
  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userService.getUserByName(username);
      Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());
      grantedAuthorities.add(grantedAuthority);

      return new org.springframework.security.core.userdetails.User(user.getUsername(),
          user.getPassword(), grantedAuthorities);
    } catch (NullPointerException e) {
      throw new UsernameNotFoundException("Invalid credentials");
    }

  }

}
