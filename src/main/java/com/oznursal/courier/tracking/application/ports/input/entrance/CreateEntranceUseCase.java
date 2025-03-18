package com.oznursal.courier.tracking.application.ports.input.entrance;

import com.oznursal.courier.tracking.domain.model.Entrance;

public interface CreateEntranceUseCase {
    Entrance createEntrance(Entrance entrance);
}
