package com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper;

import com.oznursal.courier.tracking.domain.model.Entrance;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.EntranceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class EntranceMapper {

    @Autowired
    private ModelMapper mapper;

    public Entrance toEntrance(EntranceEntity entranceEntity) {
        return mapper.map(entranceEntity, Entrance.class);
    }

    public EntranceEntity toEntranceEntity(Entrance entrance) {
        return mapper.map(entrance, EntranceEntity.class);
    }
}
