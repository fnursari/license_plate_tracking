package com.project.payload.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SearchResponse {

    private String name;
    private String surname;
    private String phoneNumber;
}
