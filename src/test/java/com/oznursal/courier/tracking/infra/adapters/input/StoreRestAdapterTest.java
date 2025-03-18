package com.oznursal.courier.tracking.infra.adapters.input;

import com.oznursal.courier.tracking.application.ports.input.store.CreateStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.DeleteStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.GetStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.UpdateStoreUseCase;
import com.oznursal.courier.tracking.domain.model.Store;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.request.StoreRequest;
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
class StoreRestAdapterTest {
    @InjectMocks
    StoreRestAdapter storeRestAdapter;

    @Mock
    GetStoreUseCase getStoreUseCase;

    @Mock
    CreateStoreUseCase createStoreUseCase;

    @Mock
    UpdateStoreUseCase updateStoreUseCase;

    @Mock
    DeleteStoreUseCase deleteStoreUseCase;

    @Mock
    ModelMapper mapper;

    @Test
    void createStore() {
        //GIVEN
        StoreRequest request = StoreRequest.builder().build();
        Store store = new Store();

        when(mapper.map(request, Store.class)).thenReturn(store);
        when(createStoreUseCase.createStore(store)).thenReturn(store);

        //WHEN
        storeRestAdapter.createStore(request);

        //THEN
        verify(createStoreUseCase).createStore(any());
    }

    @Test
    void getStore() {
        //GIVEN
        Long storeId = 1L;
        Store store = new Store();
        store.setId(storeId);

        when(getStoreUseCase.getStore(storeId)).thenReturn(store);

        //WHEN
        storeRestAdapter.getStore(storeId);

        //THEN
        verify(getStoreUseCase).getStore(any());
    }

    @Test
    void getStores() {
        //GIVEN
        Long storeId = 1L;
        Store store = new Store();
        store.setId(storeId);

        when(getStoreUseCase.getStores()).thenReturn(List.of(store));

        //WHEN
        storeRestAdapter.getStores();

        //THEN
        verify(getStoreUseCase).getStores();
    }

    @Test
    void updateStore() {
        //GIVEN
        Long storeId = 1L;
        Store store = new Store();
        store.setId(storeId);
        StoreRequest storeRequest = new StoreRequest();
        when(mapper.map(storeRequest, Store.class)).thenReturn(store);
        doReturn(store).when(updateStoreUseCase).updateStore(storeId, store);

        //WHEN
        storeRestAdapter.updateStore(storeId, storeRequest);

        //THEN
        verify(updateStoreUseCase).updateStore(anyLong(), any());
    }

    @Test
    void deleteStore() {
        //GIVEN
        doNothing().when(deleteStoreUseCase).deleteStore(1L);

        //WHEN
        storeRestAdapter.deleteStore(1L);

        //THEN
        verify(deleteStoreUseCase).deleteStore(any());
    }

    @Test
    void deleteStores() {
        //GIVEN
        doNothing().when(deleteStoreUseCase).deleteStores();

        //WHEN
        storeRestAdapter.deleteStores();

        //THEN
        verify(deleteStoreUseCase).deleteStores();

    }
}