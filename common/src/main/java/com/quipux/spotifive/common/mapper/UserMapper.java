package com.quipux.spotifive.common.mapper;

import com.quipux.spotifive.common.request.UserCreateRequest;
import com.quipux.spotifive.common.response.LoginResponse;
import com.quipux.spotifive.domain.model.UserRecord;

import java.time.LocalDateTime;

public class UserMapper {
    private UserMapper() {
        throw new IllegalStateException("UserMapper");
    }

    public static UserRecord mapToCreate(UserCreateRequest request, String encodedPassword) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUsername(request.getUsername());
        userRecord.setPassword(encodedPassword);
        userRecord.setEmail(request.getEmail());
        userRecord.setStatus(1L);
        return userRecord;
    }

    public static UserRecord mapToLoginSuccessful(UserRecord user, String refreshTokenEncrypted) {
        user.setRefreshToken(refreshTokenEncrypted);
        user.setRefreshTokenExpiration(LocalDateTime.now().plusDays(3));
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static LoginResponse mapToLoginResponse(String accessToken, String refreshToken) {
        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

    public static UserRecord mapToLogout(UserRecord user) {
        user.setRefreshToken(null);
        user.setRefreshTokenExpiration(null);
        user.setUpdatedAt(LocalDateTime.now());
        return  user;
    }
}
