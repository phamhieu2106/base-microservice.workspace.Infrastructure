package com.base.function;

import com.base.CacheUtils;
import com.base.common.CommonConstant;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
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
        if (cacheUtils.isSetMember(CommonConstant.AuthKey.ACTIVE_USERNAME, username)) {
            logger.error(">>>>>>> Username is already in use with username={} ", username);
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        }


        cacheUtils.addToSet(CommonConstant.AuthKey.ACTIVE_USERNAME, username);
        return username;
    }
}
