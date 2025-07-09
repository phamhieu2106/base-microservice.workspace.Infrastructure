package com.base.resource;

import com.base.domain.response.WrapResponse;
import com.base.function.*;
import com.base.request.ForgotUserPasswordRequest;
import com.base.request.SignInRequest;
import com.base.request.SignUpRequest;
import com.base.request.UpdateUserPasswordRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("oauth/authenticate")
public class AuthenticateResource extends BaseResource {

    @PostMapping("/sign-in")
    public CompletableFuture<WrapResponse<String>> signIn(@Valid @RequestBody SignInRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(SignInFunc.class).exec(request)), executorService);
    }

    @PostMapping("/sign-up")
    public CompletableFuture<WrapResponse<String>> signUp(@Valid @RequestBody SignUpRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(SignUpFunc.class).exec(request)), executorService);
    }

    @PostMapping("/forgot-password")
    public CompletableFuture<WrapResponse<Boolean>> forgotPassword(@Valid @RequestBody ForgotUserPasswordRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(ForgotUserPasswordFunc.class).exec(request)), executorService);
    }

    @GetMapping("/confirm-active/{token}")
    public CompletableFuture<WrapResponse<Boolean>> confirm(@PathVariable String token) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(ConfirmActiveUserFunc.class).exec(token)), executorService);
    }

    @PutMapping("/confirm-update-password/{token}")
    public CompletableFuture<WrapResponse<Boolean>> confirmUpdateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest request,
                                                                              @PathVariable String token) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(ConfirmUpdateUserPasswordFunc.class).exec(request, token)), executorService);
    }
}
