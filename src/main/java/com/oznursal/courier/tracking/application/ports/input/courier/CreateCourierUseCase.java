package com.oznursal.courier.tracking.application.ports.input.courier;

import com.oznursal.courier.tracking.domain.model.Courier;

public interface CreateCourierUseCase {
    Courier createCourier(Courier courier);
}
