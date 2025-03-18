package com.oznursal.courier.tracking.infra.adapters.output.persistence.repository;

import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.EntranceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntranceRepository extends JpaRepository<EntranceEntity, Long> {
    List<EntranceEntity> findEntranceByCourierIdOrderByEnteredAtDesc(Long courierId);
}
