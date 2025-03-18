package com.oznursal.courier.tracking.domain.service;

import com.oznursal.courier.tracking.application.ports.output.CourierPort;
import com.oznursal.courier.tracking.domain.model.Courier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierServiceTest {
    @InjectMocks
    private CourierService courierService;

    @Mock
    CourierPort courierPort;

    @Test
    void createCourier() {
        //GIVEN
        Courier courierRequest = new Courier();
        when(courierPort.saveCourier(courierRequest)).thenReturn(courierRequest);

        //WHEN
        Courier courier = courierService.createCourier(courierRequest);

        //THEN
        assertNotNull(courier);
    }

    @Test
    void getCourier() {
        //GIVEN
        Courier courierRequest = new Courier();
        courierRequest.setId(1L);
        when(courierPort.getCourierById(courierRequest.getId())).thenReturn(Optional.of(courierRequest));

        //WHEN
        Courier courier = courierService.getCourier(courierRequest.getId());

        //THEN
        assertNotNull(courier);
    }

    @Test
    void getCouriers() {
        //GIVEN
        Courier courierRequest = new Courier();
        when(courierPort.getCouriers()).thenReturn(List.of(courierRequest));

        //WHEN
        List<Courier> couriers = courierService.getCouriers();

        //THEN
        assertNotNull(couriers);
    }

    @Test
    void getTotalDistances() {
        //GIVEN
        Courier courierRequest = new Courier();
        long courierId = 1L;
        courierRequest.setId(courierId);
        when(courierPort.getTotalTravelDistanceById(courierId)).thenReturn(100D);

        //WHEN
        Double distance = courierService.getTotalDistances(courierId);

        //THEN
        assertEquals(100D, distance);
    }

    @Test
    void deleteCourier() {
        //GIVEN
        Courier courierRequest = new Courier();
        long courierId = 1L;
        courierRequest.setId(courierId);
        doNothing().when(courierPort).deleteCourierById(courierId);

        //WHEN
        courierService.deleteCourier(courierId);

        //THEN
        verify(courierPort).deleteCourierById(anyLong());
    }

    @Test
    void deleteCouriers() {
        //GIVEN
        doNothing().when(courierPort).deleteCouriers();

        //WHEN
        courierService.deleteCouriers();

        //THEN
        verify(courierPort).deleteCouriers();
    }

    @Test
    void updateCourier() {
        //GIVEN
        Courier courierRequest = new Courier();
        long courierId = 1L;
        courierRequest.setId(courierId);
        when(courierPort.updateCourier(courierId, courierRequest)).thenReturn(courierRequest);

        //WHEN
        courierService.updateCourier(courierId, courierRequest);

        //THEN
        verify(courierPort).updateCourier(anyLong(), any());
    }
}