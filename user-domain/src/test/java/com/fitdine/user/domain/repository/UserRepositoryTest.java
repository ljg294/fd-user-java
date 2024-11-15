package com.fitdine.user.domain.repository;

import com.fitdine.user.common.code.Gender;
import com.fitdine.user.domain.DomainIntegrationTest;
import com.fitdine.user.domain.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DomainIntegrationTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        // Prepare a UserEntity using @Builder
        user = UserEntity.builder()
                .userId(null) // ID will be auto-generated
                .email("test@example.com")
                .password("password123")
                .name("John Doe")
                .age((byte) 25)
                .gender(Gender.MALE)
                .mobile("010-1234-5678")
                .signupDate(LocalDate.now())
                .build();
    }

    @Test
    void testSaveUser() {
        // Save the user to the repository
        UserEntity savedUser = userRepository.save(user);

        // Assert that the user was saved correctly
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getName()).isEqualTo("John Doe");
        assertThat(savedUser.getAge()).isEqualTo((byte) 25);
        assertThat(savedUser.getSignupDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void testFindById() {
        // Save the user and retrieve by ID
        UserEntity savedUser = userRepository.save(user);
        Optional<UserEntity> retrievedUser = userRepository.findById(savedUser.getUserId());

        // Assert the retrieved user
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getEmail()).isEqualTo("test@example.com");
        assertThat(retrievedUser.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindByEmail() {
        // Save the user
        userRepository.save(user);

        // Retrieve the user by email
        Optional<UserEntity> retrievedUser = userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equals("test@example.com"))
                .findFirst();

        // Assert the retrieved user
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testUpdateUser() {
        // Save the user
        UserEntity savedUser = userRepository.save(user);

        // Update the user's name
        savedUser = UserEntity.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .name("Jane Doe") // Updated name
                .age(savedUser.getAge())
                .gender(savedUser.getGender())
                .mobile(savedUser.getMobile())
                .signupDate(savedUser.getSignupDate())
                .build();

        UserEntity updatedUser = userRepository.save(savedUser);

        // Assert the updated name
        assertThat(updatedUser.getName()).isEqualTo("Jane Doe");
    }

    @Test
    void testDeleteUser() {
        // Save the user
        UserEntity savedUser = userRepository.save(user);

        // Delete the user
        userRepository.deleteById(savedUser.getUserId());

        // Assert that the user no longer exists
        Optional<UserEntity> deletedUser = userRepository.findById(savedUser.getUserId());
        assertThat(deletedUser).isEmpty();
    }
}
