package com.oznursal.courier.tracking.application.ports.input.entrance;

public interface DeleteEntranceUseCase {
    void deleteEntrance(Long id);

    void deleteEntrances();
}
