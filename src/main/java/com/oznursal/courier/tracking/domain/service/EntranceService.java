package com.oznursal.courier.tracking.domain.service;

import com.oznursal.courier.tracking.application.ports.input.entrance.CreateEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.DeleteEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.GetEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.UpdateEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.output.EntrancePort;
import com.oznursal.courier.tracking.domain.exception.EntranceNotFoundException;
import com.oznursal.courier.tracking.domain.model.Entrance;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
public class EntranceService implements CreateEntranceUseCase, GetEntranceUseCase, UpdateEntranceUseCase, DeleteEntranceUseCase {
    private final EntrancePort entrancePort;

    private final Logger logger = LoggerFactory.getLogger(EntranceService.class);

    @Override
    public Entrance createEntrance(Entrance entrance) {
        logger.info("Create Entrance");
        return entrancePort.saveEntrance(entrance);
    }

    @Override
    public Entrance getEntrance(Long id) {
        logger.info("Get Entrance");
        return entrancePort.getEntranceById(id).orElseThrow(() -> new EntranceNotFoundException("Entrance not found related to id: " + id));
    }

    @Override
    public List<Entrance> getEntrances() {
        logger.info("Get Entrances");
        return entrancePort.getEntrances();
    }

    @Override
    public void deleteEntrance(Long id) {
        logger.info("Delete Entrance");
        entrancePort.deleteEntranceById(id);
    }

    @Override
    public void deleteEntrances() {
        logger.info("Delete Entrances");
        entrancePort.deleteEntrances();
    }

    @Override
    public Entrance updateEntrance(Long id, Entrance entrance) {
        logger.info("Update Entrance with id: {}", id);
        return entrancePort.updateEntrance(id,entrance);
    }
}
