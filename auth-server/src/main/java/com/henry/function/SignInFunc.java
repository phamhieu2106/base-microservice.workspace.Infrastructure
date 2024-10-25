package com.henry.function;

import com.henry.base.service.func.BaseFunc;
import com.henry.domain.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignInFunc extends BaseFunc {
    public String exec(SignInRequest request) {
        return null;
    }
}
