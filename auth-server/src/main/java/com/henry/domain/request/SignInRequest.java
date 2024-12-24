package com.henry.domain.request;

import com.henry.base.domain.request.BaseRequest;
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
