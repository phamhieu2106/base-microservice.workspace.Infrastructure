package com.base.function;

import com.base.constant.AuthErrorCode;
import com.base.constant.UserStatus;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.grpc.InternalUserServiceGrpc;
import com.base.grpc.InternalUserServiceOuterClass;
import com.base.request.SignInRequest;
import com.base.response.UserDetailResponse;
import com.base.util.MappingUtils;
import com.base.utils.GrpcHandlerUtils;
import com.base.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SignInFunc extends BaseFunc {

    @SuppressWarnings("unused")
    @GrpcClient("auth-query")
    private InternalUserServiceGrpc.InternalUserServiceBlockingStub internalUserServiceBlockingStub;

    private final JwtUtils jwtUtils;

    public String exec(SignInRequest request) {
        UserDetailResponse userDetailResponse = getUserDetailResponse(request);
        if (Objects.equals(UserStatus.BLOCKED, userDetailResponse.getStatus())) {
            throw new ServiceException(AuthErrorCode.USER_BLOCKED);
        }

        return jwtUtils.extractUsername(userDetailResponse.getUsername());
    }

    private UserDetailResponse getUserDetailResponse(SignInRequest request) {
        InternalUserServiceOuterClass.FindUserByUsernameAndPasswordRequest internalRequest = InternalUserServiceOuterClass.FindUserByUsernameAndPasswordRequest.newBuilder()
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .build();

        InternalUserServiceOuterClass.UserView userView = GrpcHandlerUtils.callInternal(()
                -> internalUserServiceBlockingStub.findUserByUsernameAndPassword(internalRequest));

        return MappingUtils.mapObject(userView, UserDetailResponse.class);
    }
}
