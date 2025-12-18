package com.quipux.spotifive.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_MODULE_PRIVILEGE")
public class UserModulePrivilegeRecord {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_MODULE_PRIVILEGE_SEQ")
    @SequenceGenerator(
            name = "USER_MODULE_PRIVILEGE_SEQ",
            sequenceName = "USER_MODULE_PRIVILEGE_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "module_privilege_id")
    private Long modulePrivilegeId;
}
