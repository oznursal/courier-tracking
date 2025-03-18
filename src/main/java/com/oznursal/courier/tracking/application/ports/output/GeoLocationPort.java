package com.oznursal.courier.tracking.application.ports.output;

import com.oznursal.courier.tracking.domain.model.GeoLocation;

import java.util.List;
import java.util.Optional;

public interface GeoLocationPort {
    GeoLocation saveGeoLocation(GeoLocation geoLocation);

    Optional<GeoLocation> getGeoLocationById(Long id);

    List<GeoLocation> getGeoLocations();

    List<GeoLocation> getGeoLocationByCourierId(Long courierId);
}
