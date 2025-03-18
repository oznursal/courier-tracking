package com.oznursal.courier.tracking.application.ports.input.store;

import com.oznursal.courier.tracking.domain.model.Store;

public interface UpdateStoreUseCase {
    Store updateStore(Long id, Store store);
}
