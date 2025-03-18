package com.oznursal.courier.tracking.domain.service;

import com.oznursal.courier.tracking.application.ports.input.courier.CreateCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.DeleteCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.GetCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.UpdateCourierUseCase;
import com.oznursal.courier.tracking.application.ports.output.CourierPort;
import com.oznursal.courier.tracking.domain.exception.CourierNotFoundException;
import com.oznursal.courier.tracking.domain.model.Courier;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class CourierService implements CreateCourierUseCase, GetCourierUseCase, UpdateCourierUseCase, DeleteCourierUseCase {
    private final CourierPort courierPort;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Courier createCourier(Courier courier) {
        logger.info("Create courier: " + courier);
        return courierPort.saveCourier(courier);
    }

    @Override
    public Courier getCourier(Long id) {
        logger.info("Get courier: " + id);
        return courierPort.getCourierById(id).orElseThrow(() -> new CourierNotFoundException("Courier not found related to id: " + id));
    }

    @Override
    public List<Courier> getCouriers() {
        logger.info("Get couriers");
        return courierPort.getCouriers();
    }

    @Override
    public Double getTotalDistances(Long courierId) {
        return courierPort.getTotalTravelDistanceById(courierId);
    }

    @Override
    public void deleteCourier(Long id) {
        logger.info("Delete courier: " + id);
        courierPort.deleteCourierById(id);
    }

    @Override
    public void deleteCouriers() {
        logger.info("Delete couriers");
        courierPort.deleteCouriers();
    }

    @Override
    public Courier updateCourier(Long id, Courier courier) {
        logger.info("Update courier: " + id);
        return courierPort.updateCourier(id, courier);
    }
}
