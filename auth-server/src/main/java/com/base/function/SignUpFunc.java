package com.base.function;

import com.base.func.BaseFunc;
import com.base.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpFunc extends BaseFunc {
    public String exec(SignUpRequest request, String currentUsername) {

        return null;
    }
}
