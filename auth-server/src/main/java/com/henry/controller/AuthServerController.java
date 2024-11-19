package com.henry.controller;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.domain.SignInRequest;
import com.henry.function.SignInFunc;
import com.henry.function.UpdateUserPasswordFunc;
import com.henry.request.user.UpdateUserPasswordRequest;
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
                -> WrapResponse.ok(applicationContext.getBean(SignInFunc.class).exec(request)));
    }

    @PostMapping("/update-user-password")
    public CompletableFuture<WrapResponse<String>> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserPasswordFunc.class).exec(request)));
    }
}
