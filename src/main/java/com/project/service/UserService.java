package com.project.service;

import com.project.entity.concretes.Admin;
import com.project.entity.concretes.Users;
import com.project.entity.enums.RoleType;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.UserDto;
import com.project.payload.request.UserRequest;
import com.project.payload.response.AdminResponse;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.UserResponse;
import com.project.repository.UserRepository;
import com.project.utils.Messages;
import com.project.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ServiceHelpers serviceHelpers;

    private final UserDto userDto;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleService userRoleService;

    public ResponseMessage<UserResponse> save(UserRequest userRequest) {

        serviceHelpers.checkDuplicate(userRequest.getEmail(), userRequest.getPlate(), userRequest.getPhoneNumber());

        Users user = userDto.mapUserRequestToUser(userRequest);
        user.setUserRole(userRoleService.getUserRole(RoleType.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return ResponseMessage.<UserResponse>builder()
                .message("User saved")
                .httpStatus(HttpStatus.CREATED)
                .object(userDto.mapUserToUserResponse(userRepository.save(user)))
                .build();
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDto::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage deleteUserById(Long userId) {
        Optional<Users> admin =isUserExists(userId);

        userRepository.deleteById(userId);

        return ResponseMessage.builder()
                .message(String.format(Messages.USER_DELETE,"User"))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private Optional<Users> isUserExists(Long id){
        Optional<Users> user = userRepository.findById(id);

        if (!userRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, id));
        } else {
            return user;
        }
    }

    public Page<UserResponse> getAllUsersByPage(int page, int size, String sort, String type) {
        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return userRepository.findAll(pageable).map(userDto::mapUserToUserResponse);
    }

    public ResponseMessage<UserResponse> update(UserRequest userRequest, Long userId) {
        Optional<Users> user = isUserExists(userId);

        //email, plate, phone number unique olmalı
        if (!ServiceHelpers.checkUniqueProperties(user.get(), userRequest)) {
            serviceHelpers.checkDuplicate(userRequest.getEmail(),
                    userRequest.getPlate(),
                    userRequest.getPhoneNumber());
        }

        Users updatedAdmin = userDto.mapUserRequestToUpdatedUser(userRequest, userId);
        updatedAdmin.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return ResponseMessage.<UserResponse>builder()
                .message(String.format(Messages.USER_UPDATE,"user"))
                .httpStatus(HttpStatus.OK)
                .object(userDto.mapUserToUserResponse(userRepository.save(updatedAdmin)))
                .build();
    }

    public ResponseMessage<UserResponse> getUserByPlate(String plate) {
        if (!userRepository.existsByPlate(plate)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"plate",plate));
        }


        return ResponseMessage.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(userDto.mapUserToUserResponse(userRepository.getAdminsByPlate(plate)))
                .build();
    }

    public ResponseMessage<UserResponse> getUserByPhoneNumber(String phone) {
        if (!userRepository.existsByPhoneNumber(phone)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"phone number",phone));
        }


        return ResponseMessage.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(userDto.mapUserToUserResponse(userRepository.getUserByPhoneNumber(phone)))
                .build();
    }

    public ResponseMessage<UserResponse> getUserByEmail(String email) {
        if (!userRepository.existsByEmail(email)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"email",email));
        }


        return ResponseMessage.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(userDto.mapUserToUserResponse(userRepository.getUserByEmail(email)))
                .build();
    }

    public List<UserResponse> getUserByName(String name) {
        if(!userRepository.existsByName(name)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"name",name));
        }

        List<Users> user = userRepository.findByName(name);

        return user.stream().map(userDto::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUserBySurname(String surname) {
        if(!userRepository.existsBySurname(surname)){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"surname",surname));
        }
        List<Users> user = userRepository.findBySurname(surname);

        return user.stream().map(userDto::mapUserToUserResponse)
                .collect(Collectors.toList());
    }
}
