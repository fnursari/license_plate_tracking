package com.project.service;

import com.project.entity.concretes.UserRole;
import com.project.entity.enums.RoleType;
import com.project.exception.ConflictException;
import com.project.repository.UserRoleRepository;
import com.project.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType){
        return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(
                ()-> new ConflictException(Messages.ROLE_NOT_FOUND)
        );
    }
}
