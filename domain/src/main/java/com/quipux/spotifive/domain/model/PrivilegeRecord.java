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
@Table(name = "PRIVILEGE")
public class PrivilegeRecord {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRIVILEGES_SEQ")
    @SequenceGenerator(
            name = "PRIVILEGES_SEQ",
            sequenceName = "PRIVILEGES_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @Id
    private Long id;
    private String name;
    private String key;
}
