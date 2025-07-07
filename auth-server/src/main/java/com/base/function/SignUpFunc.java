package com.base.function;

import com.base.CacheUtils;
import com.base.common.CommonConstant;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.grpc.InternalUserServiceGrpc;
import com.base.grpc.InternalUserServiceOuterClass;
import com.base.request.SignUpRequest;
import com.base.util.MappingUtils;
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
            logger.error(">>>>>>> Username is already in use with username={} ", username);
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        }

        return execWithTransaction(() -> runInternal(request, username));
    }

    private String runInternal(SignUpRequest request, String username) {
        String tokenConfirm = jwtUtils.generateToken(username);
        cacheUtils.storeKeyWithMinutes(tokenConfirm, username, 15);

        InternalUserServiceOuterClass.CreateUserRequest createUserRequest = MappingUtils.mapObject(request, InternalUserServiceOuterClass.CreateUserRequest.class);
        internalUserServiceBlockingStub.createUser(createUserRequest);
        return tokenConfirm;
    }
}
