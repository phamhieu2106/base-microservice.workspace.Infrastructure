package com.base.function;

import com.base.constant.RabbitMQQueue;
import com.base.func.BaseFunc;
import com.base.grpc.InternalUserServiceGrpc;
import com.base.grpc.InternalUserServiceOuterClass;
import com.base.model.SystemNotifyModel;
import com.base.request.ForgotUserPasswordRequest;
import com.base.util.QueueUtils;
import com.base.utils.GrpcHandlerUtils;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ForgotUserPasswordFunc extends BaseFunc {

    @SuppressWarnings("unused")
    @GrpcClient("auth-query")
    private InternalUserServiceGrpc.InternalUserServiceBlockingStub internalUserServiceBlockingStub;

    public Boolean exec(ForgotUserPasswordRequest request) {
        return execWithTransaction(() -> runInternal(request));
    }

    private Boolean runInternal(ForgotUserPasswordRequest request) {

        if (isUserExist(request.getEmail())) {
            QueueUtils.sendQueue(RabbitMQQueue.QUEUE_SEND_SYSTEM_NOTIFICATION, SystemNotifyModel.builder()
                    .build());
        }

        return true;
    }

    private Boolean isUserExist(String email) {
        return GrpcHandlerUtils.callInternal(() -> {
            InternalUserServiceOuterClass.FindByEmailRequest internalRequest = InternalUserServiceOuterClass.FindByEmailRequest.newBuilder()
                    .setEmail(email)
                    .build();

            InternalUserServiceOuterClass.UserView userView = internalUserServiceBlockingStub.findUserByEmail(internalRequest);
            return Objects.nonNull(userView);
        });
    }
}
