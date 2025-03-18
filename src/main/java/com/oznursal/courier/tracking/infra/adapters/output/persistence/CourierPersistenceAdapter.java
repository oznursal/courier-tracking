package com.oznursal.courier.tracking.infra.adapters.output.persistence;

import com.oznursal.courier.tracking.application.ports.output.CourierPort;
import com.oznursal.courier.tracking.domain.exception.CourierNotFoundException;
import com.oznursal.courier.tracking.domain.model.Courier;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.CourierEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.GeoLocationEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.CourierMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.CourierRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.GeoLocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CourierPersistenceAdapter implements CourierPort {
    private final CourierRepository courierRepository;

    private final GeoLocationRepository geoLocationRepository;

    private final CourierMapper courierMapper;

    private final Logger logger = LoggerFactory.getLogger(CourierPersistenceAdapter.class);

    @Override
    @Transactional
    public Courier saveCourier(Courier courier) {
        CourierEntity courierEntity = courierMapper.toCourierEntity(courier);
        courierRepository.save(courierEntity);
        logger.info("Courier saved to database.");
        return courierMapper.toCourier(courierEntity);
    }

    @Override
    public Optional<Courier> getCourierById(Long id) {
        Optional<CourierEntity> courierEntity = courierRepository.findById(id);

        if (courierEntity.isEmpty()) {
            logger.warn("Courier with id {} not found.", id);
            return Optional.empty();
        }

        logger.info("Retrieved courier with id {}.", id);
        Courier courier = courierMapper.toCourier(courierEntity.get());
        return Optional.of(courier);
    }

    @Override
    public List<Courier> getCouriers() {
        List<CourierEntity> courierEntities = courierRepository.findAll();
        logger.info("Retrieved Couriers: {}", courierEntities);
        return courierEntities.stream().map(courierMapper::toCourier).toList();
    }

    @Override
    public void deleteCourierById(Long id) {
        courierRepository.deleteById(id);
        logger.info("Courier with id {} deleted.", id);
    }

    @Override
    public void deleteCouriers() {
        courierRepository.deleteAll();
        logger.info("Couriers deleted.");
    }

    @Override
    public Courier updateCourier(Long id, Courier courier) {
        Optional<CourierEntity> courierEntityOptional = courierRepository.findById(id);
        if (courierEntityOptional.isEmpty()) {
            throw new CourierNotFoundException("Courier not found");
        }
        var courierEntity = courierMapper.toCourierEntity(courier);
        courierRepository.save(courierEntity);
        logger.info("Updated Courier: {}", courier);
        return courierMapper.toCourier(courierEntity);
    }

    @Override
    public Double getTotalTravelDistanceById(Long id) {
        List<GeoLocationEntity> locationEntities = geoLocationRepository.findGeoLocationsByCourierId(id);
        logger.info("Retrieved total travel distance for courier with id {}.", id);
        return locationEntities.getLast().getTotalDistance();
    }
}
