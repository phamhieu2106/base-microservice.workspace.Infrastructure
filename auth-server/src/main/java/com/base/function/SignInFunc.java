package com.base.function;

import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.constant.AuthErrorCode;
import com.base.request.SignInRequest;
import com.base.util.JwtUtil;
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
            return jwtUtil.generateToken(authentication.getName());
        } catch (BadCredentialsException e) {
            logger.error(">>>>>> SignInFunc: {}", e.getMessage());
            throw new ServiceException(AuthErrorCode.USER_NOT_FOUND);
        }
    }
}
