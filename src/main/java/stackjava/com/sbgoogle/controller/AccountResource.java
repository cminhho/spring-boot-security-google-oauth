package stackjava.com.sbgoogle.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stackjava.com.sbgoogle.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountResource {

  private static class AccountResourceException extends RuntimeException {
    private AccountResourceException(String message) {
      super(message);
    }
  }

  private final UserService userService;

  public AccountResource(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/account")
  public ResponseEntity<UserDetails> getAccount() {
    if(!userService.getUserWithAuthorities().isPresent()){
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return ResponseEntity.ok().body(userService.getUserWithAuthorities().get());
  }
}
