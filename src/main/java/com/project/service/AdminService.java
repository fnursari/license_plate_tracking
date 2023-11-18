package com.project.service;

import com.project.entity.concretes.Admin;
import com.project.entity.enums.RoleType;
import com.project.exception.ForbiddenException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.AdminDto;
import com.project.payload.request.AdminRequest;
import com.project.payload.response.AdminResponse;
import com.project.payload.response.ResponseMessage;
import com.project.repository.AdminRepository;
import com.project.utils.Messages;
import com.project.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final ServiceHelpers serviceHelpers;

    private final AdminDto adminDto;

    private final UserRoleService userRoleService;



    public ResponseMessage<AdminResponse> save(AdminRequest adminRequest) {
        serviceHelpers.checkDuplicate(adminRequest.getEmail(), adminRequest.getPlate(), adminRequest.getPhoneNumber());

        Admin admin = adminDto.mapAdminRequestToAdmin(adminRequest);
        admin.setBuilt_in(false);
        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

        if (Objects.equals(adminRequest.getEmail(),"fnursari96@gmail.com")){
            admin.setBuilt_in(true);
        }

        return ResponseMessage.<AdminResponse>builder()
                .message("Admin saved")
                .httpStatus(HttpStatus.CREATED)
                .object(adminDto.mapAdminToAdminResponse(adminRepository.save(admin)))
                .build();
    }

    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(adminDto::mapAdminToAdminResponse)
                .collect(Collectors.toList());

    }


    public ResponseMessage deleteAdminById(Long userId) {
        Admin admin =isAdminExists(userId);

        if(admin.isBuilt_in()){
            throw new ForbiddenException(Messages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        adminRepository.deleteById(userId);

        return ResponseMessage.builder()
                .message(String.format(Messages.USER_DELETE,"Admin"))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Page<AdminResponse> getAllAdminsByPage(int page, int size, String sort, String type) {
        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return adminRepository.findAll(pageable).map(adminDto::mapAdminToAdminResponse);

    }

    public ResponseMessage<AdminResponse> getAdminByPlate(String plate) {
        if (!adminRepository.existsByPlate(plate)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"plate",plate));
        }


        return ResponseMessage.<AdminResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(adminDto.mapAdminToAdminResponse(adminRepository.getAdminsByPlate(plate)))
                .build();
    }


//    public ResponseMessage<List<AdminResponse>> getAdminByName(String name) {
//        if(adminRepository.existsByName(name)){
//            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"name",name));
//        }
//        List<Admin> admin = adminRepository.findByNameContaining(name);
//
//
//        return ResponseMessage.<List<AdminResponse>>builder()
//                .httpStatus(HttpStatus.OK)
//                .object(admin.stream().map(adminDto::mapAdminToAdminResponse)
//                        .collect(Collectors.toList()))
//                .build();
//
//    }



    private Admin isAdminExists(Long id){
        return adminRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE,id)));
    }


}
