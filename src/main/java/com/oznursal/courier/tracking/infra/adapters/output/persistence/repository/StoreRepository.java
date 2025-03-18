package com.oznursal.courier.tracking.infra.adapters.output.persistence.repository;

import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
}
