package com.fitdine.user.domain.repository;

import com.fitdine.user.common.code.Gender;
import com.fitdine.user.domain.DomainIntegrationTest;
import com.fitdine.user.domain.entity.DinerEntity;
import com.fitdine.user.domain.entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DomainIntegrationTest
class DinerRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DinerRepository dinerRepository;

    private MemberEntity owner;

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
    }

    @AfterEach
    void tearDown() {
        dinerRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("음식점 생성 성공 테스트")
    void createDinerSuccessful() {
        // Arrange
        DinerEntity diner = DinerEntity.createBuilder()
                .ownerMember(owner)
                .dinerName("Jane's Diner")
                .dinerAddress("123 Dine Street")
                .build();

        // Act
        DinerEntity savedDiner = dinerRepository.save(diner);

        // Assert
        assertThat(savedDiner.getDinerId()).isNotNull();
        assertThat(savedDiner.getOwnerMember()).isEqualTo(owner);
        assertThat(savedDiner.getDinerName()).isEqualTo("Jane's Diner");
        assertThat(savedDiner.getDinerAddress()).isEqualTo("123 Dine Street");
    }

    @Test
    @DisplayName("음식점 ID로 조회 성공 테스트")
    void findDinerByIdSuccessful() {
        // Arrange
        DinerEntity diner = DinerEntity.createBuilder()
                .ownerMember(owner)
                .dinerName("Jane's Diner")
                .dinerAddress("123 Dine Street")
                .build();
        DinerEntity savedDiner = dinerRepository.save(diner);

        // Act
        Optional<DinerEntity> retrievedDiner = dinerRepository.findById(savedDiner.getDinerId());

        // Assert
        assertThat(retrievedDiner).isPresent();
        assertThat(retrievedDiner.get().getDinerName()).isEqualTo("Jane's Diner");
        assertThat(retrievedDiner.get().getDinerAddress()).isEqualTo("123 Dine Street");
    }

    @Test
    @DisplayName("음식점 소유자 ID로 음식점 조회 테스트")
    void findDinersByOwnerMemberSuccessful() {
        // Arrange
        DinerEntity diner1 = DinerEntity.createBuilder()
                .ownerMember(owner)
                .dinerName("Diner 1")
                .dinerAddress("123 Dine Street")
                .build();

        DinerEntity diner2 = DinerEntity.createBuilder()
                .ownerMember(owner)
                .dinerName("Diner 2")
                .dinerAddress("456 Food Avenue")
                .build();

        dinerRepository.saveAll(List.of(diner1, diner2));

        // Act
        List<DinerEntity> diners = dinerRepository.findAllByOwnerMember(owner);

        // Assert
        assertThat(diners).hasSize(2);
        assertThat(diners).extracting(DinerEntity::getDinerName).containsExactlyInAnyOrder("Diner 1", "Diner 2");
    }

    @Test
    @Transactional
    @DisplayName("음식점 업데이트 성공 테스트")
    void updateDinerSuccessful() {
        // Arrange
        DinerEntity diner = DinerEntity.createBuilder()
                .ownerMember(owner)
                .dinerName("Original Diner")
                .dinerAddress("123 Original Street")
                .build();
        DinerEntity savedDiner = dinerRepository.save(diner);

        // 수정할 데이터
        MemberEntity newOwner = MemberEntity.builder()
                .email("newowner@example.com")
                .password("newpassword")
                .name("New Owner")
                .age((byte) 40)
                .gender(Gender.MALE)
                .mobile("010-0000-0000")
                .signupDate(LocalDate.now())
                .build();
        memberRepository.save(newOwner);

        String updatedName = "Updated Diner";
        String updatedAddress = "456 Updated Street";

        // Act
        savedDiner.update(newOwner, updatedName, updatedAddress);
        DinerEntity updatedDiner = dinerRepository.save(savedDiner);

        // Assert
        assertThat(updatedDiner.getDinerName()).isEqualTo(updatedName);
        assertThat(updatedDiner.getDinerAddress()).isEqualTo(updatedAddress);
        assertThat(updatedDiner.getOwnerMember()).isEqualTo(newOwner);
    }

    @Test
    @DisplayName("음식점 삭제 성공 테스트")
    void deleteDinerSuccessful() {
        // Arrange
        DinerEntity diner = DinerEntity.createBuilder()
                .ownerMember(owner)
                .dinerName("Jane's Diner")
                .dinerAddress("123 Dine Street")
                .build();
        DinerEntity savedDiner = dinerRepository.save(diner);

        // Act
        dinerRepository.deleteById(savedDiner.getDinerId());

        // Assert
        Optional<DinerEntity> deletedDiner = dinerRepository.findById(savedDiner.getDinerId());
        assertThat(deletedDiner).isEmpty();
    }
}
