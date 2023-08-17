package com.example.einkaufsliste.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDto {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String name;

}
