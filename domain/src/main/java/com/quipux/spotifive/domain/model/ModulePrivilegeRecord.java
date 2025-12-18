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
@Table(name = "MODULE_PRIVILEGES")
public class ModulePrivilegeRecord {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULE_PRIVILEGES_SEQ")
    @SequenceGenerator(
            name = "MODULE_PRIVILEGES_SEQ",
            sequenceName = "MODULE_PRIVILEGES_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @Id
    private Long id;
    private Long moduleId;
    private Long privilegeId;
    private String key;
}
