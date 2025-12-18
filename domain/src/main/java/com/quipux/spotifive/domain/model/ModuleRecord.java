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
@Table(name = "MODULES")
public class ModuleRecord {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULES_SEQ")
    @SequenceGenerator(
            name = "MODULES_SEQ",
            sequenceName = "MODULES_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @Id
    private Long id;
    private String name;
    @Column(name = "\"key\"")
    private String key;
}
