package com.fitdine.user.domain.repository;

import com.fitdine.user.domain.entity.DinerEntity;
import com.fitdine.user.domain.entity.DinerScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinerScheduleRepository extends JpaRepository<DinerScheduleEntity, Long> {

    List<DinerScheduleEntity> findAllByDiner(DinerEntity diner);

}
