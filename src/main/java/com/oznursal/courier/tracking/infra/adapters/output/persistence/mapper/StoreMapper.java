package com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper;

import com.oznursal.courier.tracking.domain.model.Store;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.StoreEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class StoreMapper {
    @Autowired
    private ModelMapper mapper;

    public Store toStore(StoreEntity storeEntity){
        return mapper.map(storeEntity, Store.class);
    }

    public StoreEntity toStoreEntity(Store store){
        return mapper.map(store, StoreEntity.class);
    }
}
