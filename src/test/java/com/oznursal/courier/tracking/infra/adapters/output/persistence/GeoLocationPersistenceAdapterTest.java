package com.oznursal.courier.tracking.infra.adapters.output.persistence;

import com.oznursal.courier.tracking.domain.exception.CourierNotFoundException;
import com.oznursal.courier.tracking.domain.model.Courier;
import com.oznursal.courier.tracking.domain.model.GeoLocation;
import com.oznursal.courier.tracking.infra.adapters.kafka.EventProducer;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.CourierEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.EntranceEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.GeoLocationEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.GeoLocationMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.CourierRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.EntranceRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.GeoLocationRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeoLocationPersistenceAdapterTest {
    @InjectMocks
    private GeoLocationPersistenceAdapter geoLocationPersistenceAdapter;

    @Mock
    GeoLocationRepository geoLocationRepository;

    @Mock
    CourierRepository courierRepository;

    @Mock
    EntranceRepository entranceRepository;

    @Mock
    StoreRepository storeRepository;

    @Mock
    GeoLocationMapper geoLocationMapper;

    @Mock
    EventProducer producer;

    @Test
    void saveGeoLocation() {
        //GIVEN
        GeoLocation geoLocation = new GeoLocation();
        Long geoLocationId = 1L;
        geoLocation.setId(geoLocationId);
        Courier courier = new Courier();
        courier.setId(1L);
        geoLocation.setCourier(courier);

        GeoLocationEntity geoLocationEntity = new GeoLocationEntity();
        geoLocationEntity.setId(geoLocationId);
        CourierEntity courierEntity = new CourierEntity();
        courierEntity.setId(1L);
        geoLocationEntity.setCourier(courierEntity);
        EntranceEntity entranceEntity = new EntranceEntity();
        entranceEntity.setEnteredAt(LocalDateTime.now());
        when(courierRepository.findById(1L)).thenReturn(Optional.of(courierEntity));
        when(geoLocationRepository.save(geoLocationEntity)).thenReturn(geoLocationEntity);
        when(geoLocationMapper.toGeoLocation(any())).thenReturn(geoLocation);
        when(geoLocationMapper.toGeoLocationEntity(any())).thenReturn(geoLocationEntity);
        when(entranceRepository.findEntranceByCourierIdOrderByEnteredAtDesc(courier.getId())).thenReturn(List.of(entranceEntity));
        doNothing().when(producer).sendGeoLocationMessage(anyString());

        //WHEN
        geoLocationPersistenceAdapter.saveGeoLocation(geoLocation);

        //THEN
        verify(geoLocationRepository).save(any());
        verify(producer).sendGeoLocationMessage(anyString());
        verify(entranceRepository).findEntranceByCourierIdOrderByEnteredAtDesc(anyLong());
    }

    @Test
    void saveGeoLocation_throws_error_if_courier_is_null() {
        //GIVEN
        GeoLocation geoLocation = new GeoLocation();
        Long geoLocationId = 1L;
        geoLocation.setId(geoLocationId);
        Courier courier = new Courier();
        courier.setId(1L);
        geoLocation.setCourier(courier);

        //WHEN - THEN
        assertThrows(CourierNotFoundException.class, () -> geoLocationPersistenceAdapter.saveGeoLocation(geoLocation));
    }

    @Test
    void getGeoLocationById_withEmptyResult() {
        //GIVEN
        GeoLocation geoLocation = new GeoLocation();
        Long geoLocationId = 1L;
        geoLocation.setId(geoLocationId);
        GeoLocationEntity geoLocationEntity = new GeoLocationEntity();
        geoLocationEntity.setId(geoLocationId);
        when(geoLocationRepository.findById(geoLocationId)).thenReturn(Optional.empty());

        //WHEN
        geoLocationPersistenceAdapter.getGeoLocationById(geoLocationId);

        //THEN
        verify(geoLocationRepository).findById(anyLong());
    }


    @Test
    void getGeoLocationById() {
        //GIVEN
        GeoLocation geoLocation = new GeoLocation();
        Long geoLocationId = 1L;
        geoLocation.setId(geoLocationId);
        GeoLocationEntity geoLocationEntity = new GeoLocationEntity();
        geoLocationEntity.setId(geoLocationId);
        when(geoLocationRepository.findById(geoLocationId)).thenReturn(Optional.of(geoLocationEntity));
        when(geoLocationMapper.toGeoLocation(any())).thenReturn(geoLocation);

        //WHEN
        geoLocationPersistenceAdapter.getGeoLocationById(geoLocationId);

        //THEN
        verify(geoLocationRepository).findById(anyLong());
    }

    @Test
    void getGeoLocations() {
        //GIVEN
        GeoLocation geoLocation = new GeoLocation();
        Long geoLocationId = 1L;
        geoLocation.setId(geoLocationId);
        GeoLocationEntity geoLocationEntity = new GeoLocationEntity();
        geoLocationEntity.setId(geoLocationId);
        when(geoLocationRepository.findAll()).thenReturn(List.of(geoLocationEntity));
        when(geoLocationMapper.toGeoLocation(any())).thenReturn(geoLocation);

        //WHEN
        geoLocationPersistenceAdapter.getGeoLocations();

        //THEN
        verify(geoLocationRepository).findAll();
    }

    @Test
    void getGeoLocationByCourierId() {
        //GIVEN
        GeoLocation geoLocation = new GeoLocation();
        Long geoLocationId = 1L;
        Long courierId = 1L;
        geoLocation.setId(geoLocationId);
        GeoLocationEntity geoLocationEntity = new GeoLocationEntity();
        geoLocationEntity.setId(geoLocationId);
        when(geoLocationRepository.findGeoLocationsByCourierId(geoLocationId)).thenReturn(List.of(geoLocationEntity));
        when(geoLocationMapper.toGeoLocation(any())).thenReturn(geoLocation);

        //WHEN
        geoLocationPersistenceAdapter.getGeoLocationByCourierId(courierId);

        //THEN
        verify(geoLocationRepository).findGeoLocationsByCourierId(any());
    }
}