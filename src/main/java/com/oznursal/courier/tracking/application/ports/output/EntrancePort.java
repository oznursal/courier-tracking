package com.oznursal.courier.tracking.application.ports.output;

import com.oznursal.courier.tracking.domain.model.Entrance;

import java.util.*;

public interface EntrancePort {
    Entrance saveEntrance(Entrance entrance);

    Optional<Entrance> getEntranceById(Long id);

    List<Entrance> getEntrances();

    void deleteEntranceById(Long id);

    void deleteEntrances();

    Entrance updateEntrance(Long id, Entrance entrance);

    List<Entrance> getEntrancesByCourierId(Long courierId);
}
