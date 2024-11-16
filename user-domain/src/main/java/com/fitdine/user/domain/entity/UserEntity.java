package com.fitdine.user.domain.entity;

import com.fitdine.user.common.code.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"}),
        @UniqueConstraint(columnNames = {"email"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true, columnDefinition = "bigint COMMENT '사용자 ID'")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "varchar(255) COMMENT '이메일'")
    @Email
    @NotNull
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(255) COMMENT '비밀번호'")
    @NotBlank
    private String password;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) COMMENT '이름'")
    @NotBlank
    private String name;

    @Column(name = "age", columnDefinition = "tinyint unsigned COMMENT '나이'")
    @Min(0)
    private Byte age;

    @Column(name = "gender", columnDefinition = "enum('Male', 'Female') COMMENT '성별'")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "mobile", columnDefinition = "varchar(15) COMMENT '휴대폰번호'")
    private String mobile;

    @Column(name = "signup_date", nullable = false, columnDefinition = "date COMMENT '가입일자'")
    @NotNull
    private LocalDate signupDate;

    @Builder
    public UserEntity(Long userId,
                      String email,
                      String password,
                      String name,
                      Byte age,
                      Gender gender,
                      String mobile,
                      LocalDate signupDate) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mobile = mobile;
        this.signupDate = signupDate;
    }
}
