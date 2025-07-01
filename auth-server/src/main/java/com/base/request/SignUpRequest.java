package com.base.request;

import com.base.base.domain.request.BaseRequest;
import com.base.constant.AuthErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest extends BaseRequest {
    @NotBlank(message = AuthErrorCode.USER_USERNAME_EMPTY)
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;
    private Date dateOfBirth;
}
