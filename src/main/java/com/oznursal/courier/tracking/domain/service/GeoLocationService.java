package com.oznursal.courier.tracking.domain.service;

import com.oznursal.courier.tracking.application.ports.input.geolocation.CreateGeoLocationUseCase;
import com.oznursal.courier.tracking.application.ports.input.geolocation.GetGeoLocationUseCase;
import com.oznursal.courier.tracking.application.ports.output.GeoLocationPort;
import com.oznursal.courier.tracking.domain.exception.GeoLocationNotFoundException;
import com.oznursal.courier.tracking.domain.model.GeoLocation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
public class GeoLocationService implements CreateGeoLocationUseCase, GetGeoLocationUseCase {
    private final GeoLocationPort geoLocationPort;

    private final Logger logger = LoggerFactory.getLogger(GeoLocationService.class);

    @Override
    public GeoLocation getGeoLocation(Long id) {
        logger.info("Get Geo Location");
        return geoLocationPort.getGeoLocationById(id).orElseThrow(() -> new GeoLocationNotFoundException("No geolocation found with id: " + id));
    }

    @Override
    public List<GeoLocation> getGeoLocations() {
        logger.info("Get Geo Locations");
        return geoLocationPort.getGeoLocations();
    }

    @Override
    public List<GeoLocation> getGeoLocationsByCourierId(Long courierId) {
        logger.info("Get Geo Locations by Courier id {}", courierId);
        return geoLocationPort.getGeoLocationByCourierId(courierId);
    }

    @Override
    public GeoLocation createGeoLocation(GeoLocation geoLocation) {
        logger.info("Create Geo Location");
        return geoLocationPort.saveGeoLocation(geoLocation);
    }
}
