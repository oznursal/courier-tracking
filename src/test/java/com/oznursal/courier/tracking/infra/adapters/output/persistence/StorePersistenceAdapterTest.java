package com.oznursal.courier.tracking.infra.adapters.output.persistence;

import com.oznursal.courier.tracking.domain.model.Store;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.entity.StoreEntity;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.StoreMapper;
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
class StorePersistenceAdapterTest {
    @InjectMocks
    StorePersistenceAdapter storePersistenceAdapter;

    @Mock
    StoreRepository storeRepository;

    @Mock
    StoreMapper storeMapper;

    @Test
    void saveStore() {
        //GIVEN
        Store store = new Store();
        StoreEntity storeEntity = new StoreEntity();

        when(storeMapper.toStoreEntity(store)).thenReturn(storeEntity);
        when(storeRepository.save(storeEntity)).thenReturn(storeEntity);

        //WHEN
        storePersistenceAdapter.saveStore(store);

        //THEN
        verify(storeRepository).save(storeEntity);
    }

    @Test
    void getStoreById() {
        //GIVEN
        Store store = new Store();
        Long storeId = 1L;
        store.setId(storeId);
        StoreEntity storeEntity = new StoreEntity();

        when(storeMapper.toStore(storeEntity)).thenReturn(store);
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(storeEntity));

        //WHEN
        storePersistenceAdapter.getStoreById(storeId);

        //THEN
        verify(storeRepository).findById(storeId);
    }

    @Test
    void getStores() {
        //GIVEN
        Store store = new Store();
        Long storeId = 1L;
        store.setId(storeId);
        StoreEntity storeEntity = new StoreEntity();

        when(storeMapper.toStore(storeEntity)).thenReturn(store);
        when(storeRepository.findAll()).thenReturn(List.of(storeEntity));

        //WHEN
        storePersistenceAdapter.getStores();

        //THEN
        verify(storeRepository).findAll();
    }

    @Test
    void deleteStoreById() {
        //GIVEN
        Store store = new Store();
        Long storeId = 1L;
        store.setId(storeId);
        doNothing().when(storeRepository).deleteById(storeId);

        //WHEN
        storePersistenceAdapter.deleteStoreById(storeId);

        //THEN
        verify(storeRepository).deleteById(storeId);
    }

    @Test
    void deleteStores() {
        //GIVEN
        doNothing().when(storeRepository).deleteAll();

        //WHEN
        storePersistenceAdapter.deleteStores();

        //THEN
        verify(storeRepository).deleteAll();
    }

    @Test
    void updateStore() {
        //GIVEN
        Store store = new Store();
        Long storeId = 1L;
        store.setId(storeId);
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setId(storeId);
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(storeEntity));
        when(storeRepository.save(storeEntity)).thenReturn(storeEntity);
        when(storeMapper.toStoreEntity(store)).thenReturn(storeEntity);

        //WHEN
        storePersistenceAdapter.updateStore(storeId, store);

        //THEN
        verify(storeRepository).save(any());
    }
}