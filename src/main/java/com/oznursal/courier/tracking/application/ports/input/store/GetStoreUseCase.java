package com.oznursal.courier.tracking.application.ports.input.store;

import com.oznursal.courier.tracking.domain.model.Store;

import java.util.List;

public interface GetStoreUseCase {
    Store getStore(Long id);

    List<Store> getStores();
}
