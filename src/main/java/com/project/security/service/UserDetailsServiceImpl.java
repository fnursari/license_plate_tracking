package com.project.security.service;

import com.project.entity.concretes.*;
import com.project.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AdminRepository adminRepository;
	private final UserRepository userRepository;



	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByEmailEquals(email);
		Users user = userRepository.findByEmailEquals(email);

		if (user != null) {
			return new UserDetailsImpl(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getPassword(),
                    user.getUserRole().getRoleType().name());
		} else if (admin != null) {
			return new UserDetailsImpl(
					admin.getId(),
					admin.getEmail(),
					admin.getName(),
					admin.getPassword(),
					admin.getUserRole().getRoleType().name());
		}
		throw new UsernameNotFoundException("User '" + email+ "' not found");
	}

}
