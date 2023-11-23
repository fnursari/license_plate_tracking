package com.project.payload.mappers;


import com.project.entity.concretes.Admin;
import com.project.entity.concretes.Users;
import com.project.payload.response.SearchResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SearchDto {

    public SearchResponse mapAdminToSearchResponse(Admin admin){

        return SearchResponse.builder()
                .name(admin.getName())
                .surname(admin.getSurname())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }
    public SearchResponse mapUserToSearchResponse(Users user){

        return SearchResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
