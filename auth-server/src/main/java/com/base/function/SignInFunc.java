package com.base.function;

import com.base.func.BaseFunc;
import com.base.request.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignInFunc extends BaseFunc {

    public String exec(SignInRequest request) {
        return request.getUsername();
    }
}
