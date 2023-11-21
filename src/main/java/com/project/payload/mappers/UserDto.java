package com.project.payload.mappers;

import com.project.entity.concretes.Users;
import com.project.entity.enums.RoleType;
import com.project.payload.request.UserRequest;
import com.project.payload.response.UserResponse;
import com.project.service.UserRoleService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDto {

    private final UserRoleService userRoleService;

    public Users mapUserRequestToUser(UserRequest userRequest){
        return Users.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .plate(userRequest.getPlate())
                .phoneNumber(userRequest.getPhoneNumber())
                .password(userRequest.getPassword())
                .build();
    }


    public UserResponse mapUserToUserResponse(Users user){
        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .plate(user.getPlate())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public Users mapUserRequestToUpdatedUser(UserRequest userRequest, Long id){
        return Users.builder()
                .id(id)
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .plate(userRequest.getPlate())
                .phoneNumber(userRequest.getPhoneNumber())
                .userRole(userRoleService.getUserRole(RoleType.USER))
                .build();

    }
}
