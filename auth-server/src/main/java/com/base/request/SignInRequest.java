package com.base.request;

import com.base.base.domain.request.BaseRequest;
import com.base.constant.AuthErrorCode;
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
    private String email;
    private String phoneNumber;
    @NotBlank(message = AuthErrorCode.PASSWORD_EMPTY)
    private String password;
}
