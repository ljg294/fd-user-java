package com.fitdine.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "diner")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DinerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dinerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_member_id", nullable = false, columnDefinition = "bigint COMMENT '음식점 보유자 ID'")
    private MemberEntity ownerMember;

    @Column(name = "diner_name", nullable = false, columnDefinition = "varchar(255) COMMENT '음식점 이름'")
    private String dinerName;

    @Column(name = "diner_address", nullable = false, columnDefinition = "varchar(255) COMMENT '음식점 주소'")
    private String dinerAddress;

    @Builder
    public DinerEntity(Long dinerId,
                       MemberEntity ownerMember,
                       String dinerName,
                       String dinerAddress) {
        this.dinerId = dinerId;
        this.ownerMember = ownerMember;
        this.dinerName = dinerName;
        this.dinerAddress = dinerAddress;
    }


    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public DinerEntity(MemberEntity ownerMember,
                       String dinerName,
                       String dinerAddress) {
        this.ownerMember = ownerMember;
        this.dinerName = dinerName;
        this.dinerAddress = dinerAddress;
    }

    public void update(MemberEntity ownerMember,
                       String dinerName,
                       String dinerAddress) {
        this.ownerMember = ownerMember;
        this.dinerName = dinerName;
        this.dinerAddress = dinerAddress;
    }
}