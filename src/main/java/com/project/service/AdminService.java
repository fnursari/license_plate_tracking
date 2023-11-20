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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;



    public ResponseMessage<AdminResponse> save(AdminRequest adminRequest) {
        serviceHelpers.checkDuplicate(adminRequest.getEmail(), adminRequest.getPlate(), adminRequest.getPhoneNumber());

        Admin admin = adminDto.mapAdminRequestToAdmin(adminRequest);
        admin.setBuilt_in(false);
        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

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
        Optional<Admin> admin =isAdminExists(userId);

        if(admin.get().isBuilt_in()){
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

    public ResponseMessage<AdminResponse> getAdminByPhoneNumber(String phone) {
        if (!adminRepository.existsByPhoneNumber(phone)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"phone number",phone));
        }


        return ResponseMessage.<AdminResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(adminDto.mapAdminToAdminResponse(adminRepository.getAdminsByPhoneNumber(phone)))
                .build();
    }


    public ResponseMessage<AdminResponse> getAdminByEmail(String email) {
        if (!adminRepository.existsByEmail(email)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"email",email));
        }


        return ResponseMessage.<AdminResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(adminDto.mapAdminToAdminResponse(adminRepository.getAdminsByEmail(email)))
                .build();
    }

    public List<AdminResponse> getAdminByName(String name) {

        if(!adminRepository.existsByName(name)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"name",name));
        }

        List<Admin> admin = adminRepository.findByName(name);

        return admin.stream().map(adminDto::mapAdminToAdminResponse)
                        .collect(Collectors.toList());

    }



    private Optional<Admin> isAdminExists(Long id){
        Optional<Admin> admin = adminRepository.findById(id);

        if (!adminRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, id));
        } else {
            return admin;
        }
    }


    public List<AdminResponse> getAdminBySurname(String surname) {
        if(!adminRepository.existsBySurname(surname)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"surname",surname));
        }
        List<Admin> admin = adminRepository.findBySurname(surname);

        return admin.stream().map(adminDto::mapAdminToAdminResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<AdminResponse> update(AdminRequest adminRequest, Long userId) {

        Optional<Admin> admin = isAdminExists(userId);

        //email, plate, phone number unique olmalı
        if (!ServiceHelpers.checkUniqueProperties(admin.get(), adminRequest)) {
            serviceHelpers.checkDuplicate(adminRequest.getEmail(),
                    adminRequest.getPlate(),
                    adminRequest.getPhoneNumber());
        }

        Admin updatedAdmin = adminDto.mapAdminRequestToUpdatedAdmin(adminRequest, userId);
        updatedAdmin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));

        return ResponseMessage.<AdminResponse>builder()
                .message(String.format(Messages.USER_UPDATE,"admin"))
                .httpStatus(HttpStatus.OK)
                .object(adminDto.mapAdminToAdminResponse(adminRepository.save(updatedAdmin)))
                .build();
    }

    public long countAllAdmins(){
        return adminRepository.count();
    }


}
