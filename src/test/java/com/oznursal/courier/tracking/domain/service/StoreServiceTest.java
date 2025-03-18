package com.oznursal.courier.tracking.domain.service;

import com.oznursal.courier.tracking.application.ports.output.StorePort;
import com.oznursal.courier.tracking.domain.model.Store;
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
class StoreServiceTest {
    @InjectMocks
    private StoreService storeService;

    @Mock
    private StorePort storePort;

    @Test
    void createStore() {
        //GIVEN
        Store storeRequest = new Store();
        when(storePort.saveStore(storeRequest)).thenReturn(storeRequest);

        //WHEN
        Store courier = storeService.createStore(storeRequest);

        //THEN
        assertNotNull(courier);
    }

    @Test
    void getStore() {
        //GIVEN
        Store storeRequest = new Store();
        storeRequest.setId(1L);
        when(storePort.getStoreById(storeRequest.getId())).thenReturn(Optional.of(storeRequest));

        //WHEN
        Store courier = storeService.getStore(storeRequest.getId());

        //THEN
        assertNotNull(courier);
    }

    @Test
    void getStores() {
        //GIVEN
        Store storeRequest = new Store();
        when(storePort.getStores()).thenReturn(List.of(storeRequest));

        //WHEN
        List<Store> stores = storeService.getStores();

        //THEN
        assertNotNull(stores);
    }

    @Test
    void deleteStore() {
        //GIVEN
        Store storeRequest = new Store();
        long courierId = 1L;
        storeRequest.setId(courierId);
        doNothing().when(storePort).deleteStoreById(courierId);

        //WHEN
        storeService.deleteStore(courierId);

        //THEN
        verify(storePort).deleteStoreById(anyLong());

    }

    @Test
    void deleteStores() {
        //GIVEN
        doNothing().when(storePort).deleteStores();

        //WHEN
        storeService.deleteStores();

        //THEN
        verify(storePort).deleteStores();
    }

    @Test
    void updateStore() {
        //GIVEN
        Store storeRequest = new Store();
        long courierId = 1L;
        storeRequest.setId(courierId);
        when(storePort.updateStore(courierId, storeRequest)).thenReturn(storeRequest);

        //WHEN
        storeService.updateStore(courierId, storeRequest);

        //THEN
        verify(storePort).updateStore(anyLong(), any());
    }
}