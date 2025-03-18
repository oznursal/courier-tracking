package com.oznursal.courier.tracking.infra.adapters.input;

import com.oznursal.courier.tracking.application.ports.input.store.CreateStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.DeleteStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.GetStoreUseCase;
import com.oznursal.courier.tracking.application.ports.input.store.UpdateStoreUseCase;
import com.oznursal.courier.tracking.domain.model.Store;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.request.StoreRequest;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.response.StoreResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/stores")
@RequiredArgsConstructor
public class StoreRestAdapter {

    private final GetStoreUseCase getStoreUseCase;

    private final CreateStoreUseCase createStoreUseCase;

    private final UpdateStoreUseCase updateStoreUseCase;

    private final DeleteStoreUseCase deleteStoreUseCase;

    private final ModelMapper mapper;

    @PostMapping
    ResponseEntity<StoreResponse> createStore(@RequestBody @Valid StoreRequest storeToCreate) {
        Store store = mapper.map(storeToCreate, Store.class);
        store = createStoreUseCase.createStore(store);
        return new ResponseEntity<>(mapper.map(store, StoreResponse.class), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable @Valid Long id) {
        Store store = getStoreUseCase.getStore(id);
        return new ResponseEntity<>(mapper.map(store, StoreResponse.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StoreResponse>> getStores() {
        List<Store> stores = getStoreUseCase.getStores();
        List<StoreResponse> storeResponses = stores.stream()
                .map(element -> mapper.map(element, StoreResponse.class)).collect(Collectors.toList());

        return new ResponseEntity<>(storeResponses, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StoreResponse> updateStore(@PathVariable("id") Long id, @RequestBody @Valid StoreRequest storeRequest) {
        Store store = mapper.map(storeRequest, Store.class);
        store = updateStoreUseCase.updateStore(id, store);
        return new ResponseEntity<>(mapper.map(store, StoreResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable("id") Long id) {
        deleteStoreUseCase.deleteStore(id);
    }

    @DeleteMapping()
    public void deleteStores() {
        deleteStoreUseCase.deleteStores();
    }
}