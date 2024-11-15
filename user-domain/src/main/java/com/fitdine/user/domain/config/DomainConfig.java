package com.fitdine.user.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan(value = {"com.fitdine.user.domain.entity"})
@EnableJpaRepositories(value = {"com.fitdine.user.domain"})
@ComponentScan(basePackages = {"com.fitdine.user.common", "com.fitdine.user.domain"})
public class DomainConfig {

}
