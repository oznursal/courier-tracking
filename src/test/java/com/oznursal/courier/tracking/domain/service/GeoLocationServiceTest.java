package com.oznursal.courier.tracking.domain.service;

import com.oznursal.courier.tracking.application.ports.output.GeoLocationPort;
import com.oznursal.courier.tracking.domain.model.Courier;
import com.oznursal.courier.tracking.domain.model.GeoLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeoLocationServiceTest {
    @InjectMocks
    private GeoLocationService geoLocationService;

    @Mock
    private GeoLocationPort geoLocationPort;

    @Test
    void getGeoLocation() {
        //GIVEN
        GeoLocation locationRequest = new GeoLocation();
        locationRequest.setId(1L);
        when(geoLocationPort.getGeoLocationById(locationRequest.getId())).thenReturn(Optional.of(locationRequest));

        //WHEN
        GeoLocation geoLocation = geoLocationService.getGeoLocation(locationRequest.getId());

        //THEN
        assertNotNull(geoLocation);
    }

    @Test
    void getGeoLocations() {
        //GIVEN
        GeoLocation locationRequest = new GeoLocation();
        when(geoLocationPort.getGeoLocations()).thenReturn(List.of(locationRequest));

        //WHEN
        List<GeoLocation> locations = geoLocationService.getGeoLocations();

        //THEN
        assertNotNull(locations);
    }

    @Test
    void getGeoLocationsByCourierId() {
        //GIVEN
        GeoLocation locationRequest = new GeoLocation();
        locationRequest.setId(1L);
        Courier courierRequest = new Courier();
        long courierId = 1L;
        courierRequest.setId(courierId);
        when(geoLocationPort.getGeoLocationByCourierId(courierId)).thenReturn(List.of(locationRequest));

        //WHEN
        List<GeoLocation> geoLocations = geoLocationService.getGeoLocationsByCourierId(courierId);

        //THEN
        assertNotNull(geoLocations);
    }

    @Test
    void createGeoLocation() {
        //GIVEN
        GeoLocation locationRequest = new GeoLocation();
        when(geoLocationPort.saveGeoLocation(locationRequest)).thenReturn(locationRequest);

        //WHEN
        GeoLocation geoLocation = geoLocationService.createGeoLocation(locationRequest);

        //THEN
        assertNotNull(geoLocation);
    }
}