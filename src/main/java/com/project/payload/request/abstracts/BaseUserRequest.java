package com.project.payload.request.abstracts;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuperBuilder
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseUserRequest {

    @NotNull(message = "Please enter your name")
    @Size(min = 2, max = 25, message = "Your name should be at least {min} chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters")
    private String name;

    @NotNull(message = "Please enter your surname")
    @Size(min = 2, max = 25, message = "Your surname should be at least {min} chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your surname must consist of the characters")
    private String surname;


    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 50, message = "Your email should be between {min} and {max} characters")
    @NotNull(message = "Please enter your email")
    private String email;


    @NotNull
    @Size(min = 5, max = 10, message = "Your plate should be at least {min} chars")
    @Pattern(regexp = "^[A-Z\\d\\s]{1,10}$", message = "Please enter a valid license plate")
    private String plate;


    @NotNull(message = "Please enter your phone number")
    @Size(min = 12, max = 20,message = "Your phone number should be 12 characters long")
    @Pattern(regexp = "^\\+(?:\\d{1,4}\\s?)?(?:[ -]?\\d{1,}){10,}$",
            message = "Please enter valid phone number")
    private String phoneNumber;


    @NotNull(message = "Please enter your password")
    @Size(min = 8, max = 60,message = "Your password should be at least {min} chars or maximum {max} characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-+_!@#$%^&*., ?]).+$", message = "Your password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (-+_!@#$%^&*., ?)." )
    private String password;


}
