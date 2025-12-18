package com.quipux.spotifive.app.aspect;

import com.quipux.spotifive.app.annotation.RequirePermission;
import com.quipux.spotifive.common.ex.PermissionDeniedException;
import com.quipux.spotifive.core.components.I18NComponent;
import com.quipux.spotifive.core.service.JwtService;
import com.quipux.spotifive.core.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {
    
    private final PermissionService permissionService;
    private final I18NComponent i18NComponent;
    private final JwtService jwtService;

    @Before("@annotation(requirePermission)")
    public void validatePermission(JoinPoint joinPoint, RequirePermission requirePermission) {
        log.info("PermissionAspect::validatePermission checking permission for {}", requirePermission.value());
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.error("PermissionAspect::validatePermission no request attributes found");
            throw new PermissionDeniedException(i18NComponent.getMessage("error.permission.no.request"));
        }
        
        HttpServletRequest request = attributes.getRequest();
        
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            log.error("PermissionAspect::validatePermission Authorization header not found or empty");
            throw new PermissionDeniedException(i18NComponent.getMessage("error.permission.token.missing"));
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            log.error("PermissionAspect::validatePermission Authorization header does not start with Bearer");
            throw new PermissionDeniedException(i18NComponent.getMessage("error.permission.token.invalid.format"));
        }
        
        Long userId;
        try {
            userId = jwtService.extractUserIdFromToken(authorizationHeader);
        } catch (Exception e) {
            log.error("PermissionAspect::validatePermission error extracting user ID from token: {}", e.getMessage());
            throw new PermissionDeniedException(i18NComponent.getMessage("error.permission.token.invalid"));
        }
        
        String permissionKey = requirePermission.value();
        permissionService.validatePermission(userId, permissionKey);
        
        log.info("PermissionAspect::validatePermission permission {} validated for user {}", permissionKey, userId);
    }
}
