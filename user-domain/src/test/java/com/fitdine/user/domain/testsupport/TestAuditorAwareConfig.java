package com.fitdine.user.domain.testsupport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class TestAuditorAwareConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(1L); // 테스트 환경에서는 항상 "1L" 반환
    }
}
