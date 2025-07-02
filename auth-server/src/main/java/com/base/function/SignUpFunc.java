package com.base.function;

import com.base.CacheUtils;
import com.base.QueueConstant;
import com.base.common.CommonConstant;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.request.SignUpRequest;
import com.base.util.GenerateUtils;
import com.base.util.QueueUtils;
import com.base.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpFunc extends BaseFunc {

    private final CacheUtils cacheUtils;

    public String exec(SignUpRequest request) {
        String username = request.getUsername();
        if (cacheUtils.isSetMember(CommonConstant.AuthCacheKey.ACTIVE_USERNAME, username)) {
            logger.error(">>>>>>> Username is already in use with username={} ", username);
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        }

        return execWithTransaction(() -> runInternal(request, username));
    }

    private String runInternal(SignUpRequest request, String username) {
        String tokenConfirm = GenerateUtils.generateUUID();
        try {
            cacheUtils.storeKeyWithMinutes(CommonConstant.AuthCacheKey.IN_WAITING_ACTIVE_USERNAME + "-" + tokenConfirm,
                    ObjectMapperUtils.mapObjectToString(request), 10);
        } catch (JsonProcessingException e) {
            logger.error(">>>> JsonProcessingException Cache sign-up request failed with request={}", request, e);
            throw new ServiceException(AuthErrorCode.CACHE_USER_FAIL);
        }

        request.setTokenConfirm(tokenConfirm);
        sendQueueNotificationSignUpUser(request);

        return username;
    }

    private void sendQueueNotificationSignUpUser(SignUpRequest request) {
        QueueUtils.sendQueue(QueueConstant.QUEUE_USER_SIGN_UP, request);
    }
}
