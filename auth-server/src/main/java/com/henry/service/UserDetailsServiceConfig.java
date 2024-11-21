package com.henry.service;

import com.henry.base.exception.ServiceException;
import com.henry.constant.AuthErrorCode;
import com.henry.model.CustomUserDetails;
import com.henry.repository.UserRepository;
import com.henry.util.MappingUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceConfig implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return MappingUtils.mapObject(userRepository.findByUsername(username)
                        .orElseThrow(() -> new ServiceException(AuthErrorCode.USER_NOT_FOUND))
                , CustomUserDetails.class);
    }
}
