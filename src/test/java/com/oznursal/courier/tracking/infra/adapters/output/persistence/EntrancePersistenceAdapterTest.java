package com.oznursal.courier.tracking.infra.adapters.output.persistence;

import com.oznursal.courier.tracking.domain.model.Courier;
import com.oznursal.courier.tracking.domain.model.Entrance;
import com.oznursal.courier.tracking.domain.model.Store;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.CourierEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.EntranceEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.StoreEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.EntranceMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.CourierRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.EntranceRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrancePersistenceAdapterTest {
    @InjectMocks
    private EntrancePersistenceAdapter entrancePersistenceAdapter;

    @Mock
    EntranceRepository entranceRepository;

    @Mock
    CourierRepository courierRepository;

    @Mock
    StoreRepository storeRepository;

    @Mock
    EntranceMapper entranceMapper;

    @Test
    void saveEntrance() {
        //GIVEN
        Entrance entrance = new Entrance();
        entrance.setId(1L);
        long courierId = 1L;
        Courier courier = new Courier();
        courier.setId(courierId);
        entrance.setCourier(courier);
        long storeId = 1L;
        Store store = new Store();
        store.setId(storeId);
        entrance.setStore(store);

        EntranceEntity entranceEntity = new EntranceEntity();
        CourierEntity courierEntity = new CourierEntity();
        StoreEntity storeEntity = new StoreEntity();

        when(entranceMapper.toEntranceEntity(entrance)).thenReturn(entranceEntity);
        when(entranceRepository.save(entranceEntity)).thenReturn(entranceEntity);
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courierEntity));
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(storeEntity));
        when(entranceRepository.save(entranceEntity)).thenReturn(entranceEntity);

        //WHEN
        entrancePersistenceAdapter.saveEntrance(entrance);

        //THEN
        verify(entranceRepository).save(entranceEntity);
    }

    @Test
    void getEntranceById() {
        //GIVEN
        Entrance entrance = new Entrance();
        Long entranceId = 1L;
        entrance.setId(entranceId);
        EntranceEntity entranceEntity = new EntranceEntity();

        when(entranceMapper.toEntrance(entranceEntity)).thenReturn(entrance);
        when(entranceRepository.findById(entranceId)).thenReturn(Optional.of(entranceEntity));

        //WHEN
        entrancePersistenceAdapter.getEntranceById(entranceId);

        //THEN
        verify(entranceRepository).findById(entranceId);
    }

    @Test
    void getEntrances() {
        //GIVEN
        Entrance entrance = new Entrance();
        Long entranceId = 1L;
        entrance.setId(entranceId);
        EntranceEntity entranceEntity = new EntranceEntity();

        when(entranceMapper.toEntrance(entranceEntity)).thenReturn(entrance);
        when(entranceRepository.findAll()).thenReturn(List.of(entranceEntity));

        //WHEN
        entrancePersistenceAdapter.getEntrances();

        //THEN
        verify(entranceRepository).findAll();
    }

    @Test
    void deleteEntranceById() {
        //GIVEN
        Entrance entrance = new Entrance();
        Long entranceId = 1L;
        entrance.setId(entranceId);
        doNothing().when(entranceRepository).deleteById(entranceId);

        //WHEN
        entrancePersistenceAdapter.deleteEntranceById(entranceId);

        //THEN
        verify(entranceRepository).deleteById(entranceId);
    }

    @Test
    void deleteEntrances() {
        //GIVEN
        doNothing().when(entranceRepository).deleteAll();

        //WHEN
        entrancePersistenceAdapter.deleteEntrances();

        //THEN
        verify(entranceRepository).deleteAll();
    }

    @Test
    void updateEntrance() {
        Entrance entrance = new Entrance();
        Long entranceId = 1L;
        entrance.setId(entranceId);
        EntranceEntity entranceEntity = new EntranceEntity();
        entranceEntity.setId(entranceId);
        when(entranceRepository.findById(entranceId)).thenReturn(Optional.of(entranceEntity));
        when(entranceRepository.save(entranceEntity)).thenReturn(entranceEntity);
        when(entranceMapper.toEntranceEntity(entrance)).thenReturn(entranceEntity);

        //WHEN
        entrancePersistenceAdapter.updateEntrance(entranceId, entrance);

        //THEN
        verify(entranceRepository).save(any());
    }

    @Test
    void getEntrancesByCourierId() {
        Entrance entrance = new Entrance();
        Long entranceId = 1L;
        Long courierId = 1L;
        entrance.setId(entranceId);
        EntranceEntity entranceEntity = new EntranceEntity();
        entranceEntity.setId(entranceId);
        when(entranceRepository.findEntranceByCourierIdOrderByEnteredAtDesc(entranceId)).thenReturn(List.of(entranceEntity));
        when(entranceMapper.toEntrance(any())).thenReturn(entrance);

        //WHEN
        entrancePersistenceAdapter.getEntrancesByCourierId(courierId);

        //THEN
        verify(entranceRepository).findEntranceByCourierIdOrderByEnteredAtDesc(any());
    }
}