package com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper;

import com.oznursal.courier.tracking.domain.model.Courier;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.CourierEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CourierMapper {
    @Autowired
    private ModelMapper mapper;

    public Courier toCourier(CourierEntity courierEntity){
        return mapper.map(courierEntity, Courier.class);
    }

    public CourierEntity toCourierEntity(Courier courier){
        return mapper.map(courier, CourierEntity.class);
    }
}
