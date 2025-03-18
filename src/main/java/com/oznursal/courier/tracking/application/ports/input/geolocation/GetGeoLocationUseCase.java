package com.oznursal.courier.tracking.application.ports.input.geolocation;

import com.oznursal.courier.tracking.domain.model.GeoLocation;

import java.util.List;

public interface GetGeoLocationUseCase {
    GeoLocation getGeoLocation(Long id);

    List<GeoLocation> getGeoLocations();

    List<GeoLocation> getGeoLocationsByCourierId(Long courierId);
}
