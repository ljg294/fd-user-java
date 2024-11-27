package com.fitdine.user.domain.entity;

import com.fitdine.user.common.code.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "varchar(255) COMMENT '이메일'")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(255) COMMENT '비밀번호'")
    private String password;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) COMMENT '이름'")
    private String name;

    @Column(name = "age", columnDefinition = "int COMMENT '나이'")
    private Byte age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "varchar(6) COMMENT '성별'")
    private Gender gender;

    @Column(name = "mobile", columnDefinition = "varchar(15) COMMENT '휴대폰번호'")
    private String mobile;

    @Column(name = "signup_date", nullable = false, columnDefinition = "date COMMENT '가입일자'")
    private LocalDate signupDate;

    @Builder
    public MemberEntity(Long memberId,
                        String email,
                        String password,
                        String name,
                        Byte age,
                        Gender gender,
                        String mobile,
                        LocalDate signupDate) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mobile = mobile;
        this.signupDate = signupDate;
    }

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public MemberEntity(String email,
                        String password,
                        String name,
                        Byte age,
                        Gender gender,
                        String mobile,
                        LocalDate signupDate) {

        validateAttributesNotNull(email, password, name, signupDate);

        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mobile = mobile;
        this.signupDate = signupDate;
    }

    public void update(String email,
                       String password,
                       String name,
                       Byte age,
                       Gender gender,
                       String mobile) {

        Assert.isTrue(email.contains("@"), "Invalid email format");
        Assert.isTrue(age >= 0, "Age cannot be negative");

        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mobile = mobile;
    }

    private void validateAttributesNotNull(String email,
                                           String password,
                                           String name,
                                           LocalDate signupDate) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(password, "password must not be null");
        Assert.notNull(name, "name must not be null");
        Assert.notNull(signupDate, "signupDate must not be null");
    }

}
