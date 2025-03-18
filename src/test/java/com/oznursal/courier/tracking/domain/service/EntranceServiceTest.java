package com.oznursal.courier.tracking.domain.service;

import com.oznursal.courier.tracking.application.ports.output.EntrancePort;
import com.oznursal.courier.tracking.domain.model.Entrance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntranceServiceTest {
    @InjectMocks
    private EntranceService entranceService;

    @Mock
    EntrancePort entrancePort;

    @Test
    void createEntrance() {
        //GIVEN
        Entrance entrance = new Entrance();
        when(entrancePort.saveEntrance(entrance)).thenReturn(entrance);

        //WHEN
        Entrance entranceResponse = entranceService.createEntrance(entrance);

        //THEN
        assertNotNull(entranceResponse);
    }

    @Test
    void getEntrance() {
        //GIVEN
        Entrance entrance = new Entrance();
        entrance.setId(1L);
        when(entrancePort.getEntranceById(entrance.getId())).thenReturn(Optional.of(entrance));

        //WHEN
        Entrance entranceResponse = entranceService.getEntrance(entrance.getId());

        //THEN
        assertNotNull(entranceResponse);
    }

    @Test
    void getEntrances() {
        //GIVEN
        Entrance entrance = new Entrance();
        when(entrancePort.getEntrances()).thenReturn(List.of(entrance));

        //WHEN
        List<Entrance> entrances = entranceService.getEntrances();

        //THEN
        assertNotNull(entrances);
    }

    @Test
    void deleteEntrance() {
        //GIVEN
        Entrance entrance = new Entrance();
        long entranceId = 1L;
        entrance.setId(entranceId);
        doNothing().when(entrancePort).deleteEntranceById(entranceId);

        //WHEN
        entranceService.deleteEntrance(entranceId);

        //THEN
        verify(entrancePort).deleteEntranceById(anyLong());
    }

    @Test
    void deleteEntrances() {
        //GIVEN
        doNothing().when(entrancePort).deleteEntrances();

        //WHEN
        entranceService.deleteEntrances();

        //THEN
        verify(entrancePort).deleteEntrances();
    }

    @Test
    void updateEntrance() {
        //GIVEN
        Entrance entrance = new Entrance();
        long entranceId = 1L;
        entrance.setId(entranceId);
        when(entrancePort.updateEntrance(entranceId, entrance)).thenReturn(entrance);

        //WHEN
        entranceService.updateEntrance(entranceId, entrance);

        //THEN
        verify(entrancePort).updateEntrance(anyLong(), any());
    }
}