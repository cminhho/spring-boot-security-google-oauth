package stackjava.com.sbgoogle.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import stackjava.com.sbgoogle.security.SecurityUtils;

@Service
public class UserService {
  public Optional<UserDetails> getUserWithAuthorities() {
    return SecurityUtils.getCurrentUserLogin();
  }
}
