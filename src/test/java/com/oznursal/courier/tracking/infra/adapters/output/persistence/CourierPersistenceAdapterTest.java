package com.oznursal.courier.tracking.infra.adapters.output.persistence;

import com.oznursal.courier.tracking.domain.model.Courier;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.CourierEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.GeoLocationEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.CourierMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.CourierRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.GeoLocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierPersistenceAdapterTest {
    @InjectMocks
    CourierPersistenceAdapter courierPersistenceAdapter;

    @Mock
    CourierRepository courierRepository;

    @Mock
    GeoLocationRepository geoLocationRepository;

    @Mock
    CourierMapper courierMapper;

    @Test
    void saveCourier() {
        //GIVEN
        Courier courier = new Courier();
        CourierEntity courierEntity = new CourierEntity();

        when(courierMapper.toCourierEntity(courier)).thenReturn(courierEntity);
        when(courierRepository.save(courierEntity)).thenReturn(courierEntity);

        //WHEN
        courierPersistenceAdapter.saveCourier(courier);

        //THEN
        verify(courierRepository).save(courierEntity);
    }

    @Test
    void getCourierById() {
        //GIVEN
        Courier courier = new Courier();
        Long courierId = 1L;
        courier.setId(courierId);
        CourierEntity courierEntity = new CourierEntity();

        when(courierMapper.toCourier(courierEntity)).thenReturn(courier);
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courierEntity));

        //WHEN
        courierPersistenceAdapter.getCourierById(courierId);

        //THEN
        verify(courierRepository).findById(courierId);
    }

    @Test
    void getCouriers() {
        //GIVEN
        Courier courier = new Courier();
        Long courierId = 1L;
        courier.setId(courierId);
        CourierEntity courierEntity = new CourierEntity();

        when(courierMapper.toCourier(courierEntity)).thenReturn(courier);
        when(courierRepository.findAll()).thenReturn(List.of(courierEntity));

        //WHEN
        courierPersistenceAdapter.getCouriers();

        //THEN
        verify(courierRepository).findAll();
    }

    @Test
    void deleteCourierById() {
        //GIVEN
        Courier courier = new Courier();
        Long courierId = 1L;
        courier.setId(courierId);
        doNothing().when(courierRepository).deleteById(courierId);

        //WHEN
        courierPersistenceAdapter.deleteCourierById(courierId);

        //THEN
        verify(courierRepository).deleteById(courierId);
    }

    @Test
    void deleteCouriers() {
        //GIVEN
        doNothing().when(courierRepository).deleteAll();

        //WHEN
        courierPersistenceAdapter.deleteCouriers();

        //THEN
        verify(courierRepository).deleteAll();
    }

    @Test
    void updateCourier() {
        //GIVEN
        Courier courier = new Courier();
        Long courierId = 1L;
        courier.setId(courierId);
        CourierEntity courierEntity = new CourierEntity();
        courierEntity.setId(courierId);
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courierEntity));
        when(courierRepository.save(courierEntity)).thenReturn(courierEntity);
        when(courierMapper.toCourierEntity(courier)).thenReturn(courierEntity);

        //WHEN
        courierPersistenceAdapter.updateCourier(courierId, courier);

        //THEN
        verify(courierRepository).save(any());
    }

    @Test
    void getTotalTravelDistanceById() {
        //GIVEN
        Long courierId = 1L;
        GeoLocationEntity geoLocationEntity = new GeoLocationEntity();
        when(geoLocationRepository.findGeoLocationsByCourierId(courierId)).thenReturn(List.of(geoLocationEntity));

        //WHEN
        courierPersistenceAdapter.getTotalTravelDistanceById(courierId);

        //THEN
        verify(geoLocationRepository).findGeoLocationsByCourierId(any());
    }
}