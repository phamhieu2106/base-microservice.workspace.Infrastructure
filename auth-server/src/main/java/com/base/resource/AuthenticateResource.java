package com.base.resource;

import com.base.domain.response.WrapResponse;
import com.base.function.ConfirmActiveUserFunc;
import com.base.function.SignInFunc;
import com.base.function.SignUpFunc;
import com.base.request.SignInRequest;
import com.base.request.SignUpRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/authenticate")
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

    @PostMapping("/confirm-active/{confirmToken}")
    public CompletableFuture<WrapResponse<SignUpRequest>> confirm(@PathVariable String confirmToken) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(ConfirmActiveUserFunc.class).exec(confirmToken)), executorService);
    }

}
