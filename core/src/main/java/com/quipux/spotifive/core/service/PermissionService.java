package com.quipux.spotifive.core.service;

import com.quipux.spotifive.common.ex.PermissionDeniedException;
import com.quipux.spotifive.core.components.I18NComponent;
import com.quipux.spotifive.domain.repository.UserModulePrivilegeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final UserModulePrivilegeRepository userModulePrivilegeRepository;
    private final I18NComponent i18NComponent;

    public void validatePermission(Long userId, String permissionKey) {
        log.info("PermissionService::validatePermission userId {} permissionKey {}", userId, permissionKey);

        if (userId == null) {
            log.warn("PermissionService::validatePermission userId is null");
            throw new PermissionDeniedException(i18NComponent.getMessage("error.permission.user.not.found"));
        }

        boolean hasPermission = userModulePrivilegeRepository.hasPermission(userId, permissionKey);

        if (!hasPermission) {
            log.warn("PermissionService::validatePermission user {} does not have permission {}", userId, permissionKey);
            throw new PermissionDeniedException(
                i18NComponent.getMessage("error.permission.denied", userId, permissionKey)
            );
        }

        log.info("PermissionService::validatePermission user {} has permission {}", userId, permissionKey);
    }
}
