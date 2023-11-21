package com.project.controller;

import com.project.payload.request.AdminRequest;
import com.project.payload.request.UserRequest;
import com.project.payload.response.AdminResponse;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.UserResponse;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public ResponseMessage<UserResponse> save(@RequestBody @Valid UserRequest userRequest){
        return userService.save(userRequest);

    }


    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }


    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage deleteUserById(@PathVariable Long userId){
        return userService.deleteUserById(userId);
    }


    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<UserResponse> getAllUsersByPage(
            @RequestParam(value = "page", defaultValue = "0")int page,
            @RequestParam(value = "size", defaultValue = "10")int size,
            @RequestParam(value = "sort", defaultValue = "name")String sort,
            @RequestParam(value = "type", defaultValue = "asc")String type

    ){
        return userService.getAllUsersByPage(page,size,sort,type);

    }


    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseMessage<UserResponse> update(@RequestBody @Valid UserRequest userRequest,
                                                 @PathVariable Long userId){
        return userService.update(userRequest, userId);

    }

    @GetMapping("/searchByPlate/{plate}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<UserResponse> getUserByPlate(@PathVariable String plate){
        return userService.getUserByPlate(plate);
    }

    @GetMapping("/searchByPhone/{phoneNumber}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<UserResponse> getUserByPhoneNumber(@PathVariable String phoneNumber){
        return userService.getUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("/searchByEmail/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<UserResponse> getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("/searchByName/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<UserResponse> getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    @GetMapping("/searchBySurname/{surname}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<UserResponse> getUserBySurname(@PathVariable String surname){
        return userService.getUserBySurname(surname);
    }



}
