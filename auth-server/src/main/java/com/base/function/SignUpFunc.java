package com.base.function;

import com.base.api.InternalAuthCommandApi;
import com.base.api.InternalAuthQueryApi;
import com.base.base.exception.ServiceException;
import com.base.base.func.BaseFunc;
import com.base.constant.AuthErrorCode;
import com.base.domain.request.SignUpRequest;
import com.base.request.CreateUserRequest;
import com.base.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpFunc extends BaseFunc {

    private final InternalAuthCommandApi internalAuthCommandApi;
    private final InternalAuthQueryApi internalAuthQueryApi;

    public String exec(SignUpRequest request, String currentUsername) {
        if (internalAuthQueryApi.exitByUsername(request.getUsername()).getData()) {
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        }

        CreateUserRequest createUserRequest = MappingUtils.mapObject(request, CreateUserRequest.class);

        return internalAuthCommandApi.create(createUserRequest, currentUsername).getData();
    }
}
