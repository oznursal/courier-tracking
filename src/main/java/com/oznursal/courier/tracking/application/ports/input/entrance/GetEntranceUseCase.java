package com.oznursal.courier.tracking.application.ports.input.entrance;

import com.oznursal.courier.tracking.domain.model.Entrance;

import java.util.List;

public interface GetEntranceUseCase {
    Entrance getEntrance(Long id);

    List<Entrance> getEntrances();
}
