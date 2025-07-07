package com.base.function;

import com.base.CacheUtils;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmActiveUserFunc extends BaseFunc {

    private final CacheUtils cacheUtils;
    private final JwtUtils jwtUtils;

    public boolean exec(String token) {
        if (!cacheUtils.exists(token)) {
            throw new ServiceException(AuthErrorCode.CACHE_USER_EXPIRE);
        }

        return jwtUtils.isValidToken(token);
    }
}
