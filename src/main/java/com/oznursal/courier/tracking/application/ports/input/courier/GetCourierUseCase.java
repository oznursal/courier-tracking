package com.oznursal.courier.tracking.application.ports.input.courier;

import com.oznursal.courier.tracking.domain.model.Courier;

import java.util.List;

public interface GetCourierUseCase {
    Courier getCourier(Long id);

    List<Courier> getCouriers();

    Double getTotalDistances(Long courierId);
}
