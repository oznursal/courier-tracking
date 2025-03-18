package com.oznursal.courier.tracking.application.ports.input.store;

public interface DeleteStoreUseCase {
    void deleteStore(Long id);

    void deleteStores();
}
