package com.base.request;

import com.base.constant.AuthErrorCode;
import com.base.domain.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequest extends BaseRequest {
    @NotBlank(message = AuthErrorCode.USER_USERNAME_EMPTY)
    private String username;
    @NotBlank(message = AuthErrorCode.PASSWORD_EMPTY)
    private String password;
}
