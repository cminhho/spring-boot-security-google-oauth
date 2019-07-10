package stackjava.com.sbgoogle.security;

import java.util.Optional;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import stackjava.com.sbgoogle.controller.AuthoritiesConstants;

public final class SecurityUtils {

  private SecurityUtils() {
  }

  /**
   * Get the login of the current user.
   *
   * @return the login of the current user.
   */
  public static Optional<UserDetails> getCurrentUserLogin() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> {
          if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser;
          }
          return null;
        });
  }

  /**
   * Check if a user is authenticated.
   *
   * @return true if the user is authenticated, false otherwise.
   */
  public static boolean isAuthenticated() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> authentication.getAuthorities().stream()
            .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(
                AuthoritiesConstants.ANONYMOUS)))
        .orElse(false);
  }
}
