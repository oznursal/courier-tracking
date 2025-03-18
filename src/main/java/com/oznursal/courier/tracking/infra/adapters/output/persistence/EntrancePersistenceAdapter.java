package com.oznursal.courier.tracking.infra.adapters.output.persistence;

import com.oznursal.courier.tracking.application.ports.output.EntrancePort;
import com.oznursal.courier.tracking.domain.exception.CourierNotFoundException;
import com.oznursal.courier.tracking.domain.exception.EntranceNotFoundException;
import com.oznursal.courier.tracking.domain.exception.StoreNotFoundException;
import com.oznursal.courier.tracking.domain.model.Entrance;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.EntranceEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.EntranceMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.CourierRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.EntranceRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EntrancePersistenceAdapter implements EntrancePort {
    private final EntranceRepository entranceRepository;

    private final CourierRepository courierRepository;

    private final StoreRepository storeRepository;

    private final EntranceMapper entranceMapper;

    private final Logger logger = LoggerFactory.getLogger(EntrancePersistenceAdapter.class);

    @Override
    @Transactional
    public Entrance saveEntrance(Entrance entrance) {
        EntranceEntity entranceEntity = entranceMapper.toEntranceEntity(entrance);
        entranceEntity.setCourier(courierRepository.findById(entrance.getCourier().getId()).orElseThrow(() -> new CourierNotFoundException("Courier not found by id: " + entrance.getCourier().getId())));
        entranceEntity.setStore(storeRepository.findById(entrance.getStore().getId()).orElseThrow(() -> new StoreNotFoundException("Store not found by id: " + entrance.getCourier().getId())));
        entranceEntity.setEnteredAt(LocalDateTime.now());
        entranceRepository.save(entranceEntity);
        logger.info("Saved Entrance: {}", entrance);
        return entranceMapper.toEntrance(entranceEntity);
    }

    @Override
    public Optional<Entrance> getEntranceById(Long id) {
        EntranceEntity entranceEntity = entranceRepository.findById(id).orElse(null);
        if (entranceEntity == null) {
            return Optional.empty();
        }
        Entrance entrance = entranceMapper.toEntrance(entranceEntity);
        logger.info("Retrieved Entrance: {}", entrance);
        return Optional.of(entrance);
    }

    @Override
    public List<Entrance> getEntrances() {
        List<EntranceEntity> entranceEntities = entranceRepository.findAll();
        logger.info("Retrieved Entrances: {}", entranceEntities);
        return entranceEntities.stream().map(entranceMapper::toEntrance).toList();
    }

    @Override
    public void deleteEntranceById(Long id) {
        entranceRepository.deleteById(id);
        logger.info("Deleted Entrance: {}", id);
    }

    @Override
    public void deleteEntrances() {
        entranceRepository.deleteAll();
        logger.info("Deleted All Entrances");
    }

    @Override
    public Entrance updateEntrance(Long id, Entrance entrance) {
        Optional<EntranceEntity> entranceEntityOptional = entranceRepository.findById(id);
        if (entranceEntityOptional.isEmpty()) {
            throw new EntranceNotFoundException("Entrance not found");
        }
        var entranceEntity = entranceMapper.toEntranceEntity(entrance);
        entranceRepository.save(entranceEntity);
        logger.info("Updated Entrance: {}", entrance);
        return entranceMapper.toEntrance(entranceEntity);
    }

    @Override
    public List<Entrance> getEntrancesByCourierId(Long courierId) {
        List<EntranceEntity> entranceEntities = entranceRepository.findEntranceByCourierIdOrderByEnteredAtDesc(courierId);
        logger.info("Retrieved Entrances: {}", entranceEntities);
        return entranceEntities.stream().map(entranceMapper::toEntrance).toList();
    }
}
