package com.base.function;

import com.base.CacheUtils;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.request.SignUpRequest;
import com.base.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmActiveUserFunc extends BaseFunc {

    private final CacheUtils cacheUtils;

    public SignUpRequest exec(String confirmToken) {
        if (!cacheUtils.exists(confirmToken)) {
            throw new ServiceException(AuthErrorCode.CACHE_USER_EXPIRE);
        }

        try {
            return ObjectMapperUtils.stringToMapObject(cacheUtils.getValue(confirmToken), SignUpRequest.class);
        } catch (JsonProcessingException e) {
            logger.error(">>>> JsonProcessingException :{} ", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
