package com.base.controller;

import com.base.base.controller.BaseController;
import com.base.base.domain.response.WrapResponse;
import com.base.domain.request.SignInRequest;
import com.base.domain.request.SignUpRequest;
import com.base.function.SignInFunc;
import com.base.function.SignUpFunc;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${prefix.api}/${spring.application.name}/authenticate")
public class AuthServerController extends BaseController {

    @PostMapping("/sign-in")
    public CompletableFuture<WrapResponse<String>> signIn(@Valid @RequestBody SignInRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(SignInFunc.class).exec(request)), executorService);
    }

    @PostMapping("/sign-up")
    public CompletableFuture<WrapResponse<String>> signUp(@Valid @RequestBody SignUpRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(SignUpFunc.class).exec(request, "guest")), executorService);
    }

}
