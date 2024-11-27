package com.fitdine.user.domain.entity;

import com.fitdine.user.domain.converter.BooleanToYNConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Convert(converter= BooleanToYNConverter.class)
    @Column(name = "delete_yn", nullable = false, columnDefinition = "varchar(1) COMMENT '삭제여부'")
    private Boolean isDeleted = Boolean.FALSE;

    @CreatedBy
    @Column(name = "created_member_id", nullable = false, updatable = false, columnDefinition = "bigint COMMENT '등록자ID'")
    private Long createdMemberId;

    @CreatedDate
    @Column(name = "created_date_time", nullable = false, updatable = false, columnDefinition = "datetime COMMENT '등록일시'")
    private LocalDateTime createDateTime;

    @LastModifiedBy
    @Column(name = "modified_member_id", nullable = false, columnDefinition = "bigint COMMENT '수정자ID'")
    private Long modifiedMemberId;

    @LastModifiedDate
    @Column(name = "modified_date_time", nullable = false, columnDefinition = "datetime COMMENT '수정일시'")
    private LocalDateTime modifiedDateTime;

    public void delete() {
        this.isDeleted = Boolean.TRUE;
    }

    public String isDeletedYn() {
        return this.isDeleted.equals(Boolean.TRUE) ? "Y" : "N";
    }

}
