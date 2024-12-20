package com.fitdine.user.domain;

import com.fitdine.user.domain.config.DomainConfig;
import com.fitdine.user.domain.testsupport.TestAuditorAwareConfig;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag("integration-test")
@Import(TestAuditorAwareConfig.class)
@ActiveProfiles({"test"})
@SpringBootTest(classes = DomainConfig.class)
@AutoConfigureDataJpa
public @interface DomainIntegrationTest {
}
