package com.quipux.spotifive.core.service;

import com.quipux.spotifive.common.constants.ErrorMessages;
import com.quipux.spotifive.common.enums.TokenType;
import com.quipux.spotifive.common.ex.SpotiFiveException;
import com.quipux.spotifive.common.ex.UnauthorisedException;
import com.quipux.spotifive.common.request.LoginRequest;
import com.quipux.spotifive.common.request.UserCreateRequest;
import com.quipux.spotifive.common.mapper.UserMapper;
import com.quipux.spotifive.common.response.LoginResponse;
import com.quipux.spotifive.core.components.I18NComponent;
import com.quipux.spotifive.domain.model.UserRecord;
import com.quipux.spotifive.domain.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserService {
    private final I18NComponent i18NComponent;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(I18NComponent i18NComponent, UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.i18NComponent = i18NComponent;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void createUser(UserCreateRequest request) {
        validateUniqueEmailOrUsername(request.getEmail(), request.getUsername());
        validatePassword(request.getPassword());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        repository.save(UserMapper.mapToCreate(request, encodedPassword) );
    }

    private void validateUniqueEmailOrUsername(String email, String username) {
        log.info("UserService::validateUniqueEmailOrUsername value[{}], value[{}]  ", email,username);
        validateEmail(email);
        validateUsername(username);
    }

    private void validateEmail(String email) {
        log.info("UserService::validateEmail email: [{}]", email);
        if(repository.existsByEmailIgnoreCase(email)){
            throw new SpotiFiveException(i18NComponent.getMessage(ErrorMessages.USER_ALREADY_EXISTS_BY_EMAIL, email));
        }
    }

    private void validateUsername(String username) {
        log.info("UserService::validateUsername username: [{}]", username);
        if(repository.existsByUsernameIgnoreCase(username)){
            throw new SpotiFiveException(i18NComponent.getMessage(ErrorMessages.USER_ALREADY_EXISTS_BY_USERNAME, username));
        }
    }

    private void validatePassword(String password) {
        log.info("UserService::validatePassword");

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[/*\\-+!¿¡?@$%]).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);

        if (password == null || !pattern.matcher(password).matches()) {
            throw new SpotiFiveException(i18NComponent.getMessage(ErrorMessages.USER_INVALID_PASSWORD));
        }
    }

    public LoginResponse login(LoginRequest request) {
        log.info("UserService::login username: [{}]", request.getUsername());
        String username = request.getUsername() != null ? request.getUsername().trim() : null;
        UserRecord user = getByUsername(username);
        basicLoginLogic(user,request.getPassword());
        List<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails userDetails = new User(String.valueOf(user.getId()), user.getPassword(), authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                String.valueOf(user.getId()),
                userDetails);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String accessToken = jwtService.generateToken(user.getId(), (long) 1.2e+6, TokenType.ACCESS);
        long threeDaysInMillis = 3L * 24 * 60 * 60 * 1000;
        String refreshToken = jwtService.generateToken(user.getId(), threeDaysInMillis, TokenType.REFRESH);
        String refreshTokenEncrypted = passwordEncoder.encode(refreshToken);
        repository.save(UserMapper.mapToLoginSuccessful(user, refreshTokenEncrypted));
        return UserMapper.mapToLoginResponse(accessToken, refreshToken);
    }

    private UserRecord getByUsername(String username) {
        log.info("UserService::getByUsername username: [{}]", username);
        return repository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UnauthorisedException(i18NComponent.getMessage(ErrorMessages.INCORRECT_USERNAME_OR_PASSWORD)));
    }

    private void basicLoginLogic(UserRecord user,String password) {
        log.info("AuthService::basicLoginLogic");
        boolean matchPassword = passwordEncoder.matches(password, user.getPassword());
        if (!matchPassword) {
            throw new UnauthorisedException(i18NComponent.getMessage(
                    ErrorMessages.INCORRECT_USERNAME_OR_PASSWORD));
        }
    }

    public List<UserRecord> findAll() {
        log.info("UserService::findAll");
        return repository.findAll();
    }

    public void logout(Long userId) {
        log.info("UserService::logout userId: {}", userId);
        UserRecord user = repository.findById(userId).orElseThrow(() ->
                new UnauthorisedException(i18NComponent.getMessage(ErrorMessages.INCORRECT_USERNAME_OR_PASSWORD)));
        repository.save(UserMapper.mapToLogout(user));
    }


}
