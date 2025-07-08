package com.base.function;

import com.base.CacheUtils;
import com.base.common.CommonConstant;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.grpc.InternalUserServiceGrpc;
import com.base.grpc.InternalUserServiceOuterClass;
import com.base.request.UpdateUserPasswordRequest;
import com.base.utils.GrpcHandlerUtils;
import com.base.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmUpdateUserPasswordFunc extends BaseFunc {

    @SuppressWarnings("unused")
    @GrpcClient("auth-command")
    private InternalUserServiceGrpc.InternalUserServiceBlockingStub internalUserServiceBlockingStub;
    private final PasswordEncoder passwordEncoder;
    private final CacheUtils cacheUtils;
    private final JwtUtils jwtUtils;

    public boolean exec(UpdateUserPasswordRequest request, String token) {
        if (!cacheUtils.exists(token)) {
            throw new ServiceException(AuthErrorCode.CACHE_USER_EXPIRE);
        } else if (!jwtUtils.isValidToken(token)) {
            throw new ServiceException(AuthErrorCode.INVALID_TOKEN);
        }

        String username = jwtUtils.extractUsername(token);
        if (cacheUtils.isSetMember(CommonConstant.AuthCacheKey.ACTIVE_USERNAME, username)) {
            throw new ServiceException(AuthErrorCode.USER_ACTIVE_ALREADY);
        }

        handleUpdateUserPassword(request, username);

        return true;
    }

    private void handleUpdateUserPassword(UpdateUserPasswordRequest request, String username) {
        GrpcHandlerUtils.callInternal(() -> {
            InternalUserServiceOuterClass.ConfirmUpdateUserPasswordRequest grpcRequest =
                    InternalUserServiceOuterClass.ConfirmUpdateUserPasswordRequest.newBuilder()
                            .setUsername(username)
                            .setNewPassword(passwordEncoder.encode(request.getPassword()))
                            .setConfirmPassword(passwordEncoder.encode(request.getConfirmPassword()))
                            .build();

            return internalUserServiceBlockingStub.confirmUpdateUserPassword(grpcRequest);
        });
    }
}
