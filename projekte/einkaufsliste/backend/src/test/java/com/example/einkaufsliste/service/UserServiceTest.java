package com.example.einkaufsliste.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.CharBuffer;
import java.util.Optional;

import com.example.einkaufsliste.dto.LoginDto;
import com.example.einkaufsliste.entity.User;
import com.example.einkaufsliste.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author alice_b
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  public static final String USERNAME = "USERNAME";
  public static final String PASSWORD = "PASSWORD";
  public static final String NAME = "NAME";
  public static final String TOKEN = "TOKEN";

  private User user;

  @BeforeEach
  public void setup() {
    user = User.builder()
        .id(1L)
        .username(USERNAME)
        .password(PASSWORD)
        .name(NAME)
        .token(TOKEN)
        .build();
  }

  @Test
  void findByLogin_givenExistingUser_thenReturnUser() {
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

    User result = userService.findByLogin(USERNAME);

    assertEquals(USERNAME, result.getUsername());
  }

  @Test
  void findByLogin_givenNotExistingUser_thenThrowException() {
    when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> userService.findByLogin(USERNAME));
  }

  @Test
  void login_givenExistingUser_thenReturnUser() {
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);

    LoginDto loginDto = new LoginDto("admin", "admin");
    User user = userService.login(loginDto);

    verify(passwordEncoder, Mockito.times(1)).matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword());

    assertEquals(USERNAME, user.getUsername());
  }

  @Test
  void login_givenNotExistingUser_thenThrowException() {
    when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

    LoginDto loginDto = new LoginDto("admin", "admin");

    assertThrows(RuntimeException.class, () -> userService.login(loginDto));;
  }

  @Test
  void login_givenExistingUserNoCorrectPassword_thenThrowException() {
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);

    LoginDto loginDto = new LoginDto("admin", "admin");

    assertThrows(RuntimeException.class, () -> userService.login(loginDto));

    verify(passwordEncoder, Mockito.times(1)).matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword());
  }

  @Test
  void register_givenNoUser_thenReturnUser() {
    when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

    given(userRepository.save(Mockito.any(User.class))).willReturn(user);

    User user = userService.register(new LoginDto("admin", "admin"));

    verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));

    assertEquals(this.user.getUsername(), user.getUsername());
  }

  // TODO (ALB) 15.08.2023: validations

}
