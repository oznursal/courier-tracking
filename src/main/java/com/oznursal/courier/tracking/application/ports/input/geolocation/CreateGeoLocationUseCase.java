package com.oznursal.courier.tracking.application.ports.input.geolocation;

import com.oznursal.courier.tracking.domain.model.GeoLocation;

public interface CreateGeoLocationUseCase {
    GeoLocation createGeoLocation(GeoLocation geoLocation);
}
