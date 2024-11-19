package com.henry.function;

import com.henry.api.InternalAuthCommandApi;
import com.henry.api.InternalAuthQueryApi;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.constant.AuthErrorCode;
import com.henry.request.user.UpdateUserPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserPasswordFunc extends BaseFunc {

    private final PasswordEncoder passwordEncoder;
    private final InternalAuthCommandApi internalAuthCommandApi;
    private final InternalAuthQueryApi internalAuthQueryApi;

    public String exec(UpdateUserPasswordRequest request) {
        if (!internalAuthQueryApi.exitByUsername(request.getUsername()).getData()) {
            throw new ServiceException(AuthErrorCode.USER_NOT_FOUND);
        }

        if (ObjectUtils.notEqual(request.getPassword(), request.getConfirmPassword())) {
            throw new ServiceException(AuthErrorCode.PASSWORD_NOT_MATCH);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setConfirmPassword(passwordEncoder.encode(request.getConfirmPassword()));

        return internalAuthCommandApi.updatePassword(request.getUsername(), request).getData();
    }
}
