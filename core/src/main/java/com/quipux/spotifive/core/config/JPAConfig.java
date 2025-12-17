package com.quipux.spotifive.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.quipux.spotifive.domain.repositories")
@EntityScan(basePackages = "com.quipux.spotifive.domain.model")
public class JPAConfig {
}
