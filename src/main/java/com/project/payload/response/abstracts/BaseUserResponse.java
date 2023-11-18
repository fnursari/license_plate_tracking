package com.project.payload.response.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseUserResponse {

    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String plate;
    private String phoneNumber;

}
