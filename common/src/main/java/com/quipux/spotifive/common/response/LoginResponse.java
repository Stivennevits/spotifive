package com.quipux.spotifive.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
