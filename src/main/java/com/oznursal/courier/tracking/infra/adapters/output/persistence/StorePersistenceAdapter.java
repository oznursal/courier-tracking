package com.oznursal.courier.tracking.infra.adapters.output.persistence;

import com.oznursal.courier.tracking.application.ports.output.StorePort;
import com.oznursal.courier.tracking.domain.exception.StoreNotFoundException;
import com.oznursal.courier.tracking.domain.model.Store;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.CourierEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.StoreEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.StoreMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StorePersistenceAdapter implements StorePort {
    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    private final Logger logger = LoggerFactory.getLogger(StorePersistenceAdapter.class);

    @Override
    @Transactional
    public Store saveStore(Store store) {
        StoreEntity storeEntity = storeMapper.toStoreEntity(store);
        storeRepository.save(storeEntity);
        logger.info("Store saved to database.");
        return storeMapper.toStore(storeEntity);
    }

    @Override
    public Optional<Store> getStoreById(Long id) {
        Optional<StoreEntity> storeEntity = storeRepository.findById(id);

        if (storeEntity.isEmpty()) {
            logger.warn("Store with id {} not found.", id);
            return Optional.empty();
        }

        logger.info("Retrieved store with id {}.", id);
        Store store = storeMapper.toStore(storeEntity.get());
        return Optional.of(store);
    }

    @Override
    public List<Store> getStores() {
        List<StoreEntity> storeEntities = storeRepository.findAll();
        logger.info("Retrieving all stores from database.");
        return storeEntities.stream().map(storeMapper::toStore).toList();
    }

    @Override
    public void deleteStoreById(Long id) {
        logger.info("Deleting store with id {}.", id);
        storeRepository.deleteById(id);
    }

    @Override
    public void deleteStores() {
        logger.info("Deleting store entities.");
        storeRepository.deleteAll();
    }

    @Override
    public Store updateStore(Long id, Store store) {
        Optional<StoreEntity> storeEntityOptional = storeRepository.findById(id);
        if (storeEntityOptional.isEmpty()) {
            throw new StoreNotFoundException("Store not found");
        }
        var storeEntity = storeMapper.toStoreEntity(store);
        storeRepository.save(storeEntity);
        logger.info("Updated Store: {}", store);

        return storeMapper.toStore(storeEntity);
    }
}
