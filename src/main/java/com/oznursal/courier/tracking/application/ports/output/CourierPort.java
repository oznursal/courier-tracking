package com.oznursal.courier.tracking.application.ports.output;

import com.oznursal.courier.tracking.domain.model.Courier;

import java.util.List;
import java.util.Optional;

public interface CourierPort {

    Courier saveCourier(Courier courier);

    Optional<Courier> getCourierById(Long id);

    List<Courier> getCouriers();

    void deleteCourierById(Long id);

    void deleteCouriers();

    Courier updateCourier(Long id, Courier courier);

    Double getTotalTravelDistanceById(Long id);
}
