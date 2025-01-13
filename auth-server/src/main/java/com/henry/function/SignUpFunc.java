package com.henry.function;

import com.henry.api.InternalAuthCommandApi;
import com.henry.api.InternalAuthQueryApi;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.constant.AuthErrorCode;
import com.henry.domain.request.SignUpRequest;
import com.henry.request.CreateUserRequest;
import com.henry.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpFunc extends BaseFunc {

    private final InternalAuthCommandApi internalAuthCommandApi;
    private final InternalAuthQueryApi internalAuthQueryApi;

    public String exec(SignUpRequest request) {
        if (internalAuthQueryApi.exitByUsername(request.getUsername()).getData()) {
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        }

        CreateUserRequest createUserRequest = MappingUtils.mapObject(request, CreateUserRequest.class);

        return internalAuthCommandApi.create(createUserRequest).getData();
    }
}
