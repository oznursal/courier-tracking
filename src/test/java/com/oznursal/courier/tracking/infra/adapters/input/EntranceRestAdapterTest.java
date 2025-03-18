package com.oznursal.courier.tracking.infra.adapters.input;

import com.oznursal.courier.tracking.application.ports.input.entrance.CreateEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.DeleteEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.GetEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.UpdateEntranceUseCase;
import com.oznursal.courier.tracking.domain.model.Entrance;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.request.EntranceRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntranceRestAdapterTest {
    @InjectMocks
    EntranceRestAdapter entranceRestAdapter;

    @Mock
    GetEntranceUseCase getEntranceUseCase;

    @Mock
    CreateEntranceUseCase createEntranceUseCase;

    @Mock
    UpdateEntranceUseCase updateEntranceUseCase;

    @Mock
    DeleteEntranceUseCase deleteEntranceUseCase;

    @Mock
    ModelMapper mapper;

    @Test
    void createEntrance() {
        //GIVEN
        EntranceRequest request = EntranceRequest.builder().build();
        Entrance entrance = new Entrance();

        when(mapper.map(request, Entrance.class)).thenReturn(entrance);
        when(createEntranceUseCase.createEntrance(entrance)).thenReturn(entrance);

        //WHEN
        entranceRestAdapter.createEntrance(request);

        //THEN
        verify(createEntranceUseCase).createEntrance(any());
    }

    @Test
    void getEntrance() {
        //GIVEN
        Long entranceId = 1L;
        Entrance entrance = new Entrance();
        entrance.setId(entranceId);

        when(getEntranceUseCase.getEntrance(entranceId)).thenReturn(entrance);

        //WHEN
        entranceRestAdapter.getEntrance(entranceId);

        //THEN
        verify(getEntranceUseCase).getEntrance(any());
    }

    @Test
    void getEntrances() {
        //GIVEN
        Long entranceId = 1L;
        Entrance entrance = new Entrance();
        entrance.setId(entranceId);

        when(getEntranceUseCase.getEntrances()).thenReturn(List.of(entrance));

        //WHEN
        entranceRestAdapter.getEntrances();

        //THEN
        verify(getEntranceUseCase).getEntrances();
    }

    @Test
    void updateEntrance() {
        //GIVEN
        Long entranceId = 1L;
        Entrance entrance = new Entrance();
        entrance.setId(entranceId);
        EntranceRequest entranceRequest = new EntranceRequest();
        when(mapper.map(entranceRequest, Entrance.class)).thenReturn(entrance);
        doReturn(entrance).when(updateEntranceUseCase).updateEntrance(entranceId, entrance);

        //WHEN
        entranceRestAdapter.updateEntrance(entranceId, entranceRequest);

        //THEN
        verify(updateEntranceUseCase).updateEntrance(anyLong(), any());
    }

    @Test
    void deleteEntrance() {
        //GIVEN
        doNothing().when(deleteEntranceUseCase).deleteEntrance(1L);

        //WHEN
        entranceRestAdapter.deleteEntrance(1L);

        //THEN
        verify(deleteEntranceUseCase).deleteEntrance(any());
    }

    @Test
    void deleteEntrances() {
        //GIVEN
        doNothing().when(deleteEntranceUseCase).deleteEntrances();

        //WHEN
        entranceRestAdapter.deleteEntrances();

        //THEN
        verify(deleteEntranceUseCase).deleteEntrances();
    }
}