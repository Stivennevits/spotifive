package com.quipux.spotifive.app.handler;

import com.quipux.spotifive.common.constants.ErrorMessages;
import com.quipux.spotifive.common.ex.*;
import com.quipux.spotifive.core.components.I18NComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

import static com.quipux.spotifive.common.ex.ErrorResponseHandlerMapper.buildResponseEntity;
import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    private final I18NComponent i18NComponent;

    public RestExceptionHandler(I18NComponent i18NComponent) {
        this.i18NComponent = i18NComponent;
    }


    @ExceptionHandler(EntityAlreadyExistException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExist(@NonNull EntityAlreadyExistException ex) {
        log.info("Inside RestExceptionHandler::handleEntityAlreadyExist Error: ", ex);
        return buildResponseEntity(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(SpotiFiveException.class)
    protected ResponseEntity<Object> handleCoralException(@NonNull SpotiFiveException ex) {
        log.info("Inside RestExceptionHandler::handleCoralException Error: ", ex);
        return buildResponseEntity(ex.getMessage(), BAD_REQUEST);
    }


    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleApiException(@NonNull ApiException ex) {
        log.info("Inside RestExceptionHandler::handleApiException Error: ", ex);
        return ErrorResponseHandlerMapper.buildResponseEntity(
                ex.getMessage(),
                ex.getStatus()
        );
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnknownException(@NonNull Exception ex) {
        log.info("Inside RestExceptionHandler::handleUnknownException Error: ", ex);
        return buildResponseEntity(i18NComponent.getMessage(ErrorMessages.HANDLER_UNKNOWN_ERROR), ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(@NonNull AccessDeniedException ex) {
        log.info("Inside RestExceptionHandler::handleAccessDeniedException Error: ", ex);
        return buildResponseEntity(i18NComponent.getMessage(ErrorMessages.HANDLER_UNAUTHORIZED_ERROR), ex.getMessage(), UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorisedException.class)
    protected ResponseEntity<Object> handleUnauthorisedException(@NonNull UnauthorisedException ex) {
        log.info("Inside RestExceptionHandler::handleUnauthorisedException Error: ", ex);
        return buildResponseEntity(ex.getMessage(), UNAUTHORIZED);
    }

}
