package com.oznursal.courier.tracking.domain.service;

import com.oznursal.courier.tracking.application.ports.input.store.CreateStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.DeleteStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.GetStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.UpdateStoreUseCase;
import com.oznursal.courier.tracking.application.ports.output.StorePort;
import com.oznursal.courier.tracking.domain.exception.StoreNotFoundException;
import com.oznursal.courier.tracking.domain.model.Store;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class StoreService implements CreateStoreUseCase, GetStoreUseCase, UpdateStoreUseCase, DeleteStoreUseCase {
    private final StorePort storePort;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Store createStore(Store store) {
        logger.info("Creating new store: " + store);
        return storePort.saveStore(store);
    }

    @Override
    public Store getStore(Long id) {
        logger.info("Get store by id: " + id);
        return storePort.getStoreById(id).orElseThrow(() -> new StoreNotFoundException("Store not found in id: " + id));
    }

    @Override
    public List<Store> getStores() {
        logger.info("Get stores");
        return storePort.getStores();
    }

    @Override
    public void deleteStore(Long id) {
        logger.info("Delete store by id: " + id);
        storePort.deleteStoreById(id);
    }

    @Override
    public void deleteStores() {
        logger.info("Delete stores");
        storePort.deleteStores();
    }

    @Override
    public Store updateStore(Long id, Store store) {
        logger.info("Update store by id: " + id);
        return storePort.updateStore(id, store);
    }
}
