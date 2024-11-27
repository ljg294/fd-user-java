package com.fitdine.user.domain.repository;

import com.fitdine.user.domain.entity.DinerEntity;
import com.fitdine.user.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinerRepository extends JpaRepository<DinerEntity, Long> {

    List<DinerEntity> findAllByOwnerMember(MemberEntity ownerMember);
}
