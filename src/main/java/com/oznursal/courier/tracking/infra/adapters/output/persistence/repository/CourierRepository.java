package com.oznursal.courier.tracking.infra.adapters.output.persistence.repository;

import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.CourierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<CourierEntity, Long> {
}
