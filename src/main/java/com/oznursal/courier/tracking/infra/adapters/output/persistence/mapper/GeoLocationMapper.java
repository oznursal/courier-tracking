package com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper;

import com.oznursal.courier.tracking.domain.model.GeoLocation;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.GeoLocationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class GeoLocationMapper {

    @Autowired
    private ModelMapper mapper;

    public GeoLocation toGeoLocation(GeoLocationEntity geoLocationEntity) {
        return mapper.map(geoLocationEntity, GeoLocation.class);
    }

    public GeoLocationEntity toGeoLocationEntity(GeoLocation geoLocation) {
        return mapper.map(geoLocation, GeoLocationEntity.class);
    }
}
