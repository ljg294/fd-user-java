package com.fitdine.user.domain.entity;

import com.fitdine.user.common.code.Days;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "diner_schedule",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"diner_id", "day_of_week"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DinerScheduleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dinerScheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diner_id", nullable = false, columnDefinition = "bigint COMMENT '음식점 ID'")
    private DinerEntity diner;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 3, columnDefinition = "varchar(3) COMMENT '요일'")
    private Days dayOfWeek;

    @Column(name = "open_time", nullable = false, columnDefinition = "time COMMENT '오픈 시간'")
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false, columnDefinition = "time COMMENT '종료 시간'")
    private LocalTime closeTime;

    @Builder
    public DinerScheduleEntity(Long dinerScheduleId,
                               DinerEntity diner,
                               Days dayOfWeek,
                               LocalTime openTime,
                               LocalTime closeTime) {
        Assert.notNull(diner, "diner must not be null");

        this.dinerScheduleId = dinerScheduleId;
        this.diner = diner;
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public DinerScheduleEntity(DinerEntity diner,
                               Days dayOfWeek,
                               LocalTime openTime,
                               LocalTime closeTime) {
        Assert.notNull(diner, "diner must not be null");

        this.diner = diner;
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void update(Days dayOfWeek,
                       LocalTime openTime,
                       LocalTime closeTime) {
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}