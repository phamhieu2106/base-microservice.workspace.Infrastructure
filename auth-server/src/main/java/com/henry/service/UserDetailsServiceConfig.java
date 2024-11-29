package com.henry.service;

import com.henry.aggregate.UserAggregate;
import com.henry.base.exception.ServiceException;
import com.henry.constant.AuthErrorCode;
import com.henry.constant.UserStatus;
import com.henry.model.CustomUserDetails;
import com.henry.repository.UserRepository;
import com.henry.util.MappingUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

@Configuration
public class UserDetailsServiceConfig implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAggregate userAggregate = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        if (Objects.equals(userAggregate.getStatus(), UserStatus.BLOCKED)) {
            throw new ServiceException(AuthErrorCode.USER_BLOCKED);
        }

        return MappingUtils.mapObject(userAggregate, CustomUserDetails.class);
    }
}
