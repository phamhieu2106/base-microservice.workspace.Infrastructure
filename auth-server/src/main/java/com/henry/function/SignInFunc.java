package com.henry.function;

import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.constant.AuthErrorCode;
import com.henry.domain.request.SignInRequest;
import com.henry.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignInFunc extends BaseFunc {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String exec(SignInRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            return jwtUtil.generateToken(authentication);
        } catch (BadCredentialsException e) {
            logger.error(">>>>>> SignInFunc: {}", e.getMessage());
            throw new ServiceException(AuthErrorCode.USER_NOT_FOUND);
        }
    }
}
