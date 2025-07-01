package com.base.function;

import com.base.CacheUtils;
import com.base.common.CommonConstant;
import com.base.constant.AuthErrorCode;
import com.base.func.BaseFunc;
import com.base.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpFunc extends BaseFunc {

    private final CacheUtils cacheUtils;

    public String exec(SignUpRequest request) {
        String username = request.getUsername();

        if (cacheUtils.exists(CommonConstant.AuthKey.USER + username)) {
            throw new SecurityException(AuthErrorCode.USERNAME_EXIT);
        }


        cacheUtils.store(CommonConstant.AuthKey.USER + username, username);
        return username;
    }
}
