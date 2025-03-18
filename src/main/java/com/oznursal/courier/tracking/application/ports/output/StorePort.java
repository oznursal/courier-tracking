package com.oznursal.courier.tracking.application.ports.output;

import com.oznursal.courier.tracking.domain.model.Store;

import java.util.List;
import java.util.Optional;

public interface StorePort {
    Store saveStore(Store store);

    Optional<Store> getStoreById(Long id);

    List<Store> getStores();

    void deleteStoreById(Long id);

    void deleteStores();

    Store updateStore(Long id, Store store);
}
