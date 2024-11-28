package com.fitdine.user.domain.repository;

import com.fitdine.user.common.code.Days;
import com.fitdine.user.common.code.Gender;
import com.fitdine.user.domain.DomainIntegrationTest;
import com.fitdine.user.domain.entity.DinerEntity;
import com.fitdine.user.domain.entity.DinerScheduleEntity;
import com.fitdine.user.domain.entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DomainIntegrationTest
class DinerScheduleRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DinerRepository dinerRepository;

    @Autowired
    private DinerScheduleRepository dinerScheduleRepository;

    private MemberEntity owner;
    private DinerEntity diner;

    @BeforeEach
    void setUp() {
        // Arrange
        owner = MemberEntity.builder()
                .email("owner@example.com")
                .password("securepassword")
                .name("Jane Doe")
                .age((byte) 30)
                .gender(Gender.FEMALE)
                .mobile("010-9876-5432")
                .signupDate(LocalDate.now())
                .build();
        memberRepository.save(owner);

        diner = DinerEntity.createBuilder()
                .ownerMember(owner)
                .dinerName("Jane's Diner")
                .dinerAddress("123 Dine Street")
                .build();
        dinerRepository.save(diner);
    }

    @AfterEach
    void tearDown() {
        dinerScheduleRepository.deleteAllInBatch();
        dinerRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("음식점 스케줄 생성 성공 테스트")
    void createDinerScheduleSuccessful() {
        // Arrange
        DinerScheduleEntity dinerSchedule = DinerScheduleEntity.createBuilder()
                .diner(diner)
                .dayOfWeek(Days.MON)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();

        // Act
        DinerScheduleEntity savedDinerSchedule = dinerScheduleRepository.save(dinerSchedule);

        // Assert
        assertThat(savedDinerSchedule.getDinerScheduleId()).isNotNull();
        assertThat(savedDinerSchedule.getDiner()).isEqualTo(diner);
        assertThat(savedDinerSchedule.getDayOfWeek()).isEqualTo(Days.MON);
        assertThat(savedDinerSchedule.getOpenTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(savedDinerSchedule.getCloseTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    @DisplayName("음식점 스케줄 중복 시간 생성 실패 테스트")
    void createDinerScheduleDuplicateTimeInASingleDayFail() {
        // Arrange
        DinerScheduleEntity dinerSchedule1 = DinerScheduleEntity.createBuilder()
                .diner(diner)
                .dayOfWeek(Days.MON)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();
        dinerScheduleRepository.save(dinerSchedule1);

        DinerScheduleEntity dinerSchedule2 = DinerScheduleEntity.createBuilder()
                .diner(diner)
                .dayOfWeek(Days.MON)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(19, 0))
                .build();

        // Act & Assert
        assertThatThrownBy(() -> dinerScheduleRepository.save(dinerSchedule2))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("could not execute statement");
    }

    @Test
    @DisplayName("음식점 스케줄 ID로 조회 성공 테스트")
    void findDinerScheduleByIdSuccessful() {
        // Arrange
        DinerScheduleEntity dinerSchedule = DinerScheduleEntity.createBuilder()
                .diner(diner)
                .dayOfWeek(Days.MON)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();
        DinerScheduleEntity savedDinerSchedule = dinerScheduleRepository.save(dinerSchedule);

        // Act
        Optional<DinerScheduleEntity> retrievedDinerSchedule = dinerScheduleRepository.findById(savedDinerSchedule.getDinerScheduleId());

        // Assert
        assertThat(retrievedDinerSchedule).isPresent();
        assertThat(retrievedDinerSchedule.get().getDayOfWeek()).isEqualTo(Days.MON);
        assertThat(retrievedDinerSchedule.get().getOpenTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(retrievedDinerSchedule.get().getCloseTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    @DisplayName("음식점 ID로 스케줄 조회 테스트")
    void findDinerSchedulesByDinerSuccessful() {
        // Arrange
        DinerScheduleEntity schedule1 = DinerScheduleEntity.createBuilder()
                .diner(diner)
                .dayOfWeek(Days.MON)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();

        DinerScheduleEntity schedule2 = DinerScheduleEntity.createBuilder()
                .diner(diner)
                .dayOfWeek(Days.TUE)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(19, 0))
                .build();

        dinerScheduleRepository.saveAll(List.of(schedule1, schedule2));

        // Act
        List<DinerScheduleEntity> schedules = dinerScheduleRepository.findAllByDiner(diner);

        // Assert
        assertThat(schedules).hasSize(2);
        assertThat(schedules).extracting(DinerScheduleEntity::getDayOfWeek).containsExactlyInAnyOrder(Days.MON, Days.TUE);
    }

    @Test
    @Transactional
    @DisplayName("음식점 스케줄 업데이트 성공 테스트")
    void updateDinerScheduleSuccessful() {
        // Arrange
        DinerScheduleEntity dinerSchedule = DinerScheduleEntity.createBuilder()
                .diner(diner)
                .dayOfWeek(Days.MON)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();
        DinerScheduleEntity savedDinerSchedule = dinerScheduleRepository.save(dinerSchedule);

        Days updatedDayOfWeek = Days.TUE;
        LocalTime updatedOpenTime = LocalTime.of(10, 0);
        LocalTime updatedCloseTime = LocalTime.of(19, 0);

        // Act
        savedDinerSchedule.update(updatedDayOfWeek, updatedOpenTime, updatedCloseTime);
        DinerScheduleEntity updatedDinerSchedule = dinerScheduleRepository.save(savedDinerSchedule);

        // Assert
        assertThat(updatedDinerSchedule.getDayOfWeek()).isEqualTo(updatedDayOfWeek);
        assertThat(updatedDinerSchedule.getOpenTime()).isEqualTo(updatedOpenTime);
        assertThat(updatedDinerSchedule.getCloseTime()).isEqualTo(updatedCloseTime);
    }

    @Test
    @DisplayName("음식점 스케줄 삭제 성공 테스트")
    void deleteDinerScheduleSuccessful() {
        // Arrange
        DinerScheduleEntity dinerSchedule = DinerScheduleEntity.createBuilder()
                .diner(diner)
                .dayOfWeek(Days.MON)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();
        DinerScheduleEntity savedDinerSchedule = dinerScheduleRepository.save(dinerSchedule);

        // Act
        dinerScheduleRepository.deleteById(savedDinerSchedule.getDinerScheduleId());

        // Assert
        Optional<DinerScheduleEntity> deletedDinerSchedule = dinerScheduleRepository.findById(savedDinerSchedule.getDinerScheduleId());
        assertThat(deletedDinerSchedule).isEmpty();
    }
}