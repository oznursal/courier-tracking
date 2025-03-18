package com.oznursal.courier.tracking.infra.adapters.input;

import com.oznursal.courier.tracking.application.ports.input.courier.CreateCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.DeleteCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.GetCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.UpdateCourierUseCase;
import com.oznursal.courier.tracking.domain.model.Courier;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.request.CourierRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierRestAdapterTest {
    @InjectMocks
    CourierRestAdapter courierRestAdapter;

    @Mock
    GetCourierUseCase getCourierUseCase;

    @Mock
    CreateCourierUseCase createCourierUseCase;

    @Mock
    UpdateCourierUseCase updateCourierUseCase;

    @Mock
    DeleteCourierUseCase deleteCourierUseCase;

    @Mock
    ModelMapper mapper;

    @Test
    void createCourier() {
        //GIVEN
        CourierRequest request = CourierRequest.builder().build();
        Courier courier = new Courier();

        when(mapper.map(request, Courier.class)).thenReturn(courier);
        when(createCourierUseCase.createCourier(courier)).thenReturn(courier);

        //WHEN
        courierRestAdapter.createCourier(request);

        //THEN
        verify(createCourierUseCase).createCourier(any());
    }

    @Test
    void getCourier() {
        //GIVEN
        Long courierId = 1L;
        Courier courier = new Courier();
        courier.setId(courierId);

        when(getCourierUseCase.getCourier(courierId)).thenReturn(courier);

        //WHEN
        courierRestAdapter.getCourier(courierId);

        //THEN
        verify(getCourierUseCase).getCourier(any());
    }

    @Test
    void getCouriers() {
        //GIVEN
        Long courierId = 1L;
        Courier courier = new Courier();
        courier.setId(courierId);

        when(getCourierUseCase.getCouriers()).thenReturn(List.of(courier));

        //WHEN
        courierRestAdapter.getCouriers();

        //THEN
        verify(getCourierUseCase).getCouriers();
    }

    @Test
    void getCourierTotalDistance() {
        //GIVEN
        Long courierId = 1L;
        Courier courier = new Courier();
        courier.setId(courierId);
        Double totalDistance = 1.0;

        when(getCourierUseCase.getTotalDistances(courierId)).thenReturn(totalDistance);

        //WHEN
        courierRestAdapter.getCourierTotalDistance(courierId);

        //THEN
        verify(getCourierUseCase).getTotalDistances(courierId);
    }

    @Test
    void updateCourier() {
        //GIVEN
        Long courierId = 1L;
        Courier courier = new Courier();
        courier.setId(courierId);
        CourierRequest courierToUpdate = new CourierRequest();
        when(mapper.map(courierToUpdate, Courier.class)).thenReturn(courier);
        doReturn(courier).when(updateCourierUseCase).updateCourier(courierId, courier);

        //WHEN
        courierRestAdapter.updateCourier(courierId, courierToUpdate);

        //THEN
        verify(updateCourierUseCase).updateCourier(anyLong(), any());
    }

    @Test
    void deleteCourier() {
        //GIVEN
        doNothing().when(deleteCourierUseCase).deleteCourier(1L);

        //WHEN
        courierRestAdapter.deleteCourier(1L);

        //THEN
        verify(deleteCourierUseCase).deleteCourier(any());
    }

    @Test
    void deleteCouriers() {
        //GIVEN
        doNothing().when(deleteCourierUseCase).deleteCouriers();

        //WHEN
        courierRestAdapter.deleteCouriers();

        //THEN
        verify(deleteCourierUseCase).deleteCouriers();
    }
}