package com.quipux.spotifive.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class UserRecord {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(
            name = "USERS_SEQ",
            sequenceName = "USERS_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiration;
    private Long failedAttempts;
    private Long status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
