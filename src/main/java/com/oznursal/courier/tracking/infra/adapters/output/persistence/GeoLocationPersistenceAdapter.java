package com.oznursal.courier.tracking.infra.adapters.output.persistence;

import com.oznursal.courier.tracking.application.ports.output.GeoLocationPort;
import com.oznursal.courier.tracking.domain.exception.CourierNotFoundException;
import com.oznursal.courier.tracking.domain.model.GeoLocation;
import com.oznursal.courier.tracking.infra.adapters.kafka.EventProducer;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.CourierEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.EntranceEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.GeoLocationEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.StoreEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.GeoLocationMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.CourierRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.EntranceRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.GeoLocationRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GeoLocationPersistenceAdapter implements GeoLocationPort {
    private final GeoLocationRepository geoLocationRepository;

    private final CourierRepository courierRepository;

    private final EntranceRepository entranceRepository;

    private final StoreRepository storeRepository;

    private final GeoLocationMapper geoLocationMapper;

    private final EventProducer producer;

    private final Logger logger = LoggerFactory.getLogger(GeoLocationPersistenceAdapter.class);

    @Override
    @Transactional
    public GeoLocation saveGeoLocation(GeoLocation geoLocation) {
        GeoLocationEntity geoLocationEntity = geoLocationMapper.toGeoLocationEntity(geoLocation);
        CourierEntity courier = courierRepository.findById(geoLocation.getCourier().getId()).orElseThrow(() -> new CourierNotFoundException("Courier not found by id: " + geoLocation.getCourier().getId()));
        geoLocationEntity.setCourier(courier);
        geoLocationEntity.setTime(LocalDateTime.now());
        List<GeoLocationEntity> lastGeoLocationEntity = geoLocationRepository.findGeoLocationsByCourierIdOrderByTimeDesc(courier.getId());
        if (!lastGeoLocationEntity.isEmpty()) {
            GeoLocationEntity lastGeoLocation = lastGeoLocationEntity.getFirst();
            Double totalDistance = lastGeoLocation.getTotalDistance();
            geoLocationEntity.setTotalDistance(totalDistance == null ? 0d : totalDistance +
                    calculateDistance(lastGeoLocation.getLatitude(), lastGeoLocation.getLongitude(), geoLocation.getLatitude(), geoLocationEntity.getLongitude()));
        }
        GeoLocationEntity locationEntity = geoLocationRepository.save(geoLocationEntity);
        logger.info("Saved GeoLocationEntity: {}", locationEntity);

        producer.sendGeoLocationMessage(locationEntity.toString());
        if (isProperEntrance(locationEntity)) {
            StoreEntity storeEntity = getNearestStore(locationEntity).get();
            EntranceEntity entranceEntity = EntranceEntity.builder()
                    .store(storeEntity)
                    .longitude(locationEntity.getLongitude())
                    .latitude(locationEntity.getLatitude())
                    .enteredAt(LocalDateTime.now())
                    .courier(courier)
                    .build();
            entranceRepository.save(entranceEntity);
            producer.sendEntranceMessage(locationEntity.toString());
            logger.info("Saved EntranceEntity: {}", entranceEntity);
        }
        return geoLocationMapper.toGeoLocation(locationEntity);
    }

    private boolean isProperEntrance(GeoLocationEntity locationEntity) {
        List<EntranceEntity> lastEntrance = entranceRepository.findEntranceByCourierIdOrderByEnteredAtDesc(locationEntity.getCourier().getId());
        if (!lastEntrance.isEmpty()) {
            EntranceEntity entranceEntity = lastEntrance.stream().findFirst().get();
            long difMinutes = Duration.between(entranceEntity.getEnteredAt().toInstant(ZoneOffset.UTC), Instant.now()).toMinutes();

            return getNearestStore(locationEntity).isPresent() && difMinutes > 1;
        }

        return getNearestStore(locationEntity).isPresent();

    }

    private Optional<StoreEntity> getNearestStore(GeoLocationEntity locationEntity) {
        return storeRepository.findAll().stream().filter(
                storeEntity ->
                        Point2D.distance(locationEntity.getLatitude(), locationEntity.getLongitude(),
                                storeEntity.getLatitude(), storeEntity.getLongitude()) <= 100.0
        ).findFirst();
    }

    @Override
    public Optional<GeoLocation> getGeoLocationById(Long id) {
        Optional<GeoLocationEntity> geoLocationEntity = geoLocationRepository.findById(id);

        if (geoLocationEntity.isEmpty()) {
            logger.warn("GeoLocation with id {} not found.", id);
            return Optional.empty();
        }

        logger.info("Retrieved geoLocation with id {}.", id);
        GeoLocation geoLocation = geoLocationMapper.toGeoLocation(geoLocationEntity.get());
        return Optional.of(geoLocation);
    }

    @Override
    public List<GeoLocation> getGeoLocations() {
        List<GeoLocationEntity> geoLocationEntities = geoLocationRepository.findAll();
        logger.info("Retrieved GeoLocations: {}", geoLocationEntities);
        return geoLocationEntities.stream().map(geoLocationMapper::toGeoLocation).toList();
    }

    @Override
    public List<GeoLocation> getGeoLocationByCourierId(Long courierId) {
        List<GeoLocationEntity> geoLocationEntities = geoLocationRepository.findGeoLocationsByCourierId(courierId);
        logger.info("Retrieved geoLocationEntities with courierId {}.", courierId);
        return geoLocationEntities.stream().map(geoLocationMapper::toGeoLocation).toList();
    }

    //Equirectangular Distance Approximation
    double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);

        return Math.sqrt(x * x + y * y) * 6371;
    }
}
