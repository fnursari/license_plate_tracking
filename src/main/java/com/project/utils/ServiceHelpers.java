package com.project.utils;

import com.project.exception.ConflictException;
import com.project.repository.AdminRepository;
import com.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ServiceHelpers {

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;


    public void checkDuplicate(String email, String plate, String phoneNumber){

        if(adminRepository.existsByEmail(email) || userRepository.existsByEmail(email)){
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_USER_EMAIL,email));
        } else if (adminRepository.existsByPlate(plate) || userRepository.existsByPlate(plate)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_USER_PLATE,plate));
        } else if (adminRepository.existsByPhoneNumber(phoneNumber) || userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_USER_PHONE_NUMBER,phoneNumber));
        }

    }

    public Pageable getPageableWithProperties(int page, int size, String sort, String type){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if(Objects.equals(type,"desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return pageable;
    }

    public Pageable getPageableWithProperties(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return pageable;
    }


}
