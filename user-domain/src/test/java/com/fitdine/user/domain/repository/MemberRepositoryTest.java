package com.fitdine.user.domain.repository;

import com.fitdine.user.common.code.Gender;
import com.fitdine.user.common.code.MemberType;
import com.fitdine.user.domain.DomainIntegrationTest;
import com.fitdine.user.domain.entity.MemberEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DomainIntegrationTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private MemberEntity member;

    @BeforeEach
    void setUp() {
        // Arrange
        member = MemberEntity.builder()
                .memberType(MemberType.CUSTOMER)
                .email("test@example.com")
                .password("password123")
                .name("John Doe")
                .age((byte) 25)
                .gender(Gender.MALE)
                .mobile("010-1234-5678")
                .signupDate(LocalDate.now())
                .build();
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원 생성 성공 테스트")
    void createMemberSuccessful() {
        // Act
        MemberEntity savedMember = memberRepository.save(member);

        // Assert
        assertThat(savedMember.getMemberId()).isNotNull();
        assertThat(savedMember.getMemberType()).isEqualTo(MemberType.CUSTOMER);
        assertThat(savedMember.getEmail()).isEqualTo("test@example.com");
        assertThat(savedMember.getName()).isEqualTo("John Doe");
        assertThat(savedMember.getAge()).isEqualTo((byte) 25);
        assertThat(savedMember.getSignupDate()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("회원 ID로 회원 조회 성공 테스트")
    void findByIdSuccessful() {
        // Arrange
        MemberEntity savedMember = memberRepository.save(member);

        // Act
        Optional<MemberEntity> retrievedMember = memberRepository.findById(savedMember.getMemberId());

        // Assert
        assertThat(retrievedMember).isPresent();
        assertThat(retrievedMember.get().getEmail()).isEqualTo("test@example.com");
        assertThat(retrievedMember.get().getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("회원 업데이트 성공 테스트")
    void updateMemberSuccessful() {
        // Arrange
        String updatedName = "superman";
        MemberEntity savedMember = memberRepository.save(member);

        // Act
        savedMember.update(
                savedMember.getEmail(),
                savedMember.getPassword(),
                updatedName, // update
                savedMember.getAge(),
                savedMember.getGender(),
                savedMember.getMobile());
        MemberEntity updatedMember = memberRepository.save(savedMember);

        // Assert the updated name
        assertThat(updatedMember.getName()).isEqualTo(updatedName);
    }

    @Test
    @DisplayName("회원 삭제 성공 테스트")
    void deleteMemberSuccessful() {
        // Arrange
        MemberEntity savedMember = memberRepository.save(member);

        // Act
        memberRepository.deleteById(savedMember.getMemberId());

        // Assert
        Optional<MemberEntity> deletedMember = memberRepository.findById(savedMember.getMemberId());
        assertThat(deletedMember).isEmpty();
    }
}
