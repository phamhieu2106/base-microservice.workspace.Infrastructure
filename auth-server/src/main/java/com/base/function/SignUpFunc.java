package com.base.function;

import com.base.CacheUtils;
import com.base.common.CommonConstant;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.grpc.InternalUserServiceGrpc;
import com.base.grpc.InternalUserServiceOuterClass;
import com.base.request.SignUpRequest;
import com.base.utils.GrpcHandlerUtils;
import com.base.utils.JwtUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class SignUpFunc extends BaseFunc {

    @SuppressWarnings("unused")
    @GrpcClient("auth-command")
    private InternalUserServiceGrpc.InternalUserServiceBlockingStub internalUserServiceBlockingStub;

    private final CacheUtils cacheUtils;
    private final JwtUtils jwtUtils;

    public SignUpFunc(CacheUtils cacheUtils, JwtUtils jwtUtils) {
        this.cacheUtils = cacheUtils;
        this.jwtUtils = jwtUtils;
    }

    public String exec(SignUpRequest request) {
        String username = request.getUsername();
        if (cacheUtils.isSetMember(CommonConstant.AuthCacheKey.ACTIVE_USERNAME, username)) {
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        } else if (cacheUtils.isSetMember(CommonConstant.AuthCacheKey.INACTIVE_USERNAME, username)) {
            throw new ServiceException(AuthErrorCode.USER_INACTIVE);
        }

        return execWithTransaction(() -> runInternal(request, username));
    }

    private String runInternal(SignUpRequest request, String username) {
        String token = jwtUtils.generateToken(username);
        cacheUtils.storeKeyWithHours(token, username, 24);
        request.setToken(token);
        GrpcHandlerUtils.callInternal(() -> {
            InternalUserServiceOuterClass.CreateUserRequest.Builder builder =
                    InternalUserServiceOuterClass.CreateUserRequest.newBuilder()
                            .setUsername(username);

            if (request.getFullName() != null) {
                builder.setFullName(request.getFullName());
            }
            if (request.getEmail() != null) {
                builder.setEmail(request.getEmail());
            }
            if (request.getPhoneNumber() != null) {
                builder.setPhoneNumber(request.getPhoneNumber());
            }
            if (request.getDateOfBirth() != null) {
                builder.setDateOfBirth(request.getDateOfBirth().getTime());
            }

            InternalUserServiceOuterClass.CreateUserRequest createUserRequest = builder.build();

            return internalUserServiceBlockingStub.createUser(createUserRequest);
        });

        cacheUtils.addToSet(CommonConstant.AuthCacheKey.INACTIVE_USERNAME, username);

        return token;
    }
}
