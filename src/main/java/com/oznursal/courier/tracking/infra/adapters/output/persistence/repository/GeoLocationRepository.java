package com.oznursal.courier.tracking.infra.adapters.output.persistence.repository;

import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.GeoLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeoLocationRepository extends JpaRepository<GeoLocationEntity, Long> {
    List<GeoLocationEntity> findGeoLocationsByCourierId(Long courierId);

    List<GeoLocationEntity> findGeoLocationsByCourierIdOrderByTimeDesc(Long courierId);
}
