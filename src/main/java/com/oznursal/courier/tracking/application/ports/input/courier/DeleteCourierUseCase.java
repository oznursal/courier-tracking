package com.oznursal.courier.tracking.application.ports.input.courier;

public interface DeleteCourierUseCase {
    void deleteCourier(Long id);

    void deleteCouriers();
}
