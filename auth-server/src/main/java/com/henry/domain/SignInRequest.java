package com.henry.domain;

import com.henry.base.service.request.BaseRequest;
import com.henry.constant.AuthErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequest extends BaseRequest {
    private String username;
    private String email;
    private String phoneNumber;
    @NotBlank(message = AuthErrorCode.PASSWORD_EMPTY)
    private String password;
}
