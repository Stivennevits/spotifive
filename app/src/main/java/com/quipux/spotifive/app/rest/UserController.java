package com.quipux.spotifive.app.rest;

import com.quipux.spotifive.common.request.LoginRequest;
import com.quipux.spotifive.common.request.UserCreateRequest;
import com.quipux.spotifive.common.response.LoginResponse;
import com.quipux.spotifive.core.service.UserService;
import com.quipux.spotifive.domain.model.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.quipux.spotifive.common.router.Router.UserAPI.*;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping(ROOT)
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreateRequest request) {
        log.info("UserController::createUser");
        service.createUser(request);
    }

    @PostMapping(LOGIN)
    @ResponseStatus(OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        log.info("UserController::login request: {}", request);
        return service.login(request);
    }

    @PostMapping(LOGOUT)
    @ResponseStatus(OK)
    public void logout(@RequestHeader("X-User-Id") Long userId) {
        log.info("UserController::logout userId: {}", userId);
        service.logout(userId);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<UserRecord> findAll() {
        log.info("UserController::findAll");
        return service.findAll();
    }



}
