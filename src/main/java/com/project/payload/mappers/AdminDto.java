package com.project.payload.mappers;

import com.project.entity.concretes.Admin;
import com.project.entity.enums.RoleType;
import com.project.payload.request.AdminRequest;
import com.project.payload.response.AdminResponse;
import com.project.service.UserRoleService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdminDto {

    private final UserRoleService userRoleService;

    public Admin mapAdminRequestToAdmin(AdminRequest adminRequest){
        return Admin.builder()
                .name(adminRequest.getName())
                .surname(adminRequest.getSurname())
                .email(adminRequest.getEmail())
                .plate(adminRequest.getPlate())
                .phoneNumber(adminRequest.getPhoneNumber())
                .password(adminRequest.getPassword())
                .build();
    }


    public AdminResponse mapAdminToAdminResponse(Admin admin){
        return AdminResponse.builder()
                .userId(admin.getId())
                .name(admin.getName())
                .surname(admin.getSurname())
                .email(admin.getEmail())
                .plate(admin.getPlate())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }

    public Admin mapAdminRequestToUpdatedAdmin(AdminRequest adminRequest, Long id){
        return Admin.builder()
                .id(id)
                .name(adminRequest.getName())
                .surname(adminRequest.getSurname())
                .email(adminRequest.getEmail())
                .plate(adminRequest.getPlate())
                .phoneNumber(adminRequest.getPhoneNumber())
                .userRole(userRoleService.getUserRole(RoleType.ADMIN))
                .build();

    }
}
