package com.project.service;

import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.SearchDto;
import com.project.payload.response.SearchResponse;
import com.project.repository.AdminRepository;
import com.project.repository.UserRepository;
import com.project.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final SearchDto searchDto;

    public SearchResponse searchByPlate(String plate) {

        if(!(adminRepository.existsByPlate(plate) || userRepository.existsByPlate(plate))){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_PARAM,"plate",plate));
        }

        if(adminRepository.existsByPlate(plate) ){
            return searchDto.mapAdminToSearchResponse(adminRepository.getAdminsByPlate(plate));
        }
        return searchDto.mapUserToSearchResponse(userRepository.getUserByPlate(plate));
    }
}
