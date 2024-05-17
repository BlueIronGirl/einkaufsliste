package de.shoppinglist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO-Class for the Register-Request
 */
@Data
public class RegisterDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    public RegisterDto(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}
