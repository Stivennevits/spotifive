package com.quipux.spotifive.domain.repository;

import com.quipux.spotifive.domain.model.UserModulePrivilegeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModulePrivilegeRepository extends JpaRepository<UserModulePrivilegeRecord, Long> {

    @Query("SELECT COUNT(ump) > 0 FROM UserModulePrivilegeRecord ump " +
           "JOIN ModulePrivilegeRecord mp ON ump.modulePrivilegeId = mp.id " +
           "WHERE ump.userId = :userId AND mp.key = :permissionKey")
    boolean hasPermission(@Param("userId") Long userId, @Param("permissionKey") String permissionKey);
}
