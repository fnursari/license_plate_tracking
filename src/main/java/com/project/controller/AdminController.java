package com.project.controller;

import com.project.payload.request.AdminRequest;
import com.project.payload.response.AdminResponse;
import com.project.payload.response.ResponseMessage;
import com.project.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/save")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<AdminResponse> save(@RequestBody @Valid AdminRequest adminRequest){
        return adminService.save(adminRequest);

    }


    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<AdminResponse> getAllAdmins(){
        return adminService.getAllAdmins();
    }


    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage deleteAdminById(@PathVariable Long userId){
        return adminService.deleteAdminById(userId);
    }


    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<AdminResponse> getAllAdminsByPage(
            @RequestParam(value = "page", defaultValue = "0")int page,
            @RequestParam(value = "size", defaultValue = "10")int size,
            @RequestParam(value = "sort", defaultValue = "name")String sort,
            @RequestParam(value = "type", defaultValue = "asc")String type

    ){
        return adminService.getAllAdminsByPage(page,size,sort,type);

    }

    @GetMapping("/searchByPlate/{plate}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<AdminResponse> getAdminByPlate(@PathVariable String plate){
        return adminService.getAdminByPlate(plate);
    }

    @GetMapping("/searchByPhone/{phoneNumber}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<AdminResponse> getAdminByPhoneNumber(@PathVariable String phoneNumber){
        return adminService.getAdminByPhoneNumber(phoneNumber);
    }

    @GetMapping("/searchByEmail/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<AdminResponse> getAdminByEmail(@PathVariable String email){
        return adminService.getAdminByEmail(email);
    }

    @GetMapping("/searchByName/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<AdminResponse> getAdminByName(@PathVariable String name){
        return adminService.getAdminByName(name);
    }

    @GetMapping("/searchBySurname/{surname}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<AdminResponse> getAdminBySurname(@PathVariable String surname){
        return adminService.getAdminBySurname(surname);
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<AdminResponse> update(@RequestBody @Valid AdminRequest adminRequest,
                                                 @PathVariable Long userId){
        return adminService.update(adminRequest, userId);

    }




}
