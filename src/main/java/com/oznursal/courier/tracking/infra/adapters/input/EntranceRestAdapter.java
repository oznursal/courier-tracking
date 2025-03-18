package com.oznursal.courier.tracking.infra.adapters.input;

import com.oznursal.courier.tracking.application.ports.input.entrance.CreateEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.DeleteEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.GetEntranceUseCase;
import com.oznursal.courier.tracking.application.ports.input.entrance.UpdateEntranceUseCase;
import com.oznursal.courier.tracking.domain.model.Entrance;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.request.EntranceRequest;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.response.EntranceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/entrances")
@RequiredArgsConstructor
public class EntranceRestAdapter {
    private final GetEntranceUseCase getEntranceUseCase;

    private final CreateEntranceUseCase createEntranceUseCase;

    private final UpdateEntranceUseCase updateEntranceUseCase;

    private final DeleteEntranceUseCase deleteEntranceUseCase;

    private final ModelMapper mapper;

    @PostMapping
    ResponseEntity<EntranceResponse> createEntrance(@RequestBody @Valid EntranceRequest entranceToCreate) {
        Entrance entrance = mapper.map(entranceToCreate, Entrance.class);
        entrance = createEntranceUseCase.createEntrance(entrance);
        return new ResponseEntity<>(mapper.map(entrance, EntranceResponse.class), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntranceResponse> getEntrance(@PathVariable @Valid Long id) {
        Entrance entrance = getEntranceUseCase.getEntrance(id);
        return new ResponseEntity<>(mapper.map(entrance, EntranceResponse.class), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<EntranceResponse>> getEntrances() {
        List<Entrance> entrances = getEntranceUseCase.getEntrances();
        List<EntranceResponse> entranceResponses = entrances.stream()
                .map(element -> mapper.map(element, EntranceResponse.class)).collect(Collectors.toList());

        return new ResponseEntity<>(entranceResponses, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EntranceResponse> updateEntrance(@PathVariable("id") Long id, @RequestBody @Valid EntranceRequest entranceRequest) {
        Entrance entrance = mapper.map(entranceRequest, Entrance.class);
        entrance = updateEntranceUseCase.updateEntrance(id, entrance);
        return new ResponseEntity<>(mapper.map(entrance, EntranceResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteEntrance(@PathVariable("id") Long id) {
        deleteEntranceUseCase.deleteEntrance(id);
    }

    @DeleteMapping()
    public void deleteEntrances() {
        deleteEntranceUseCase.deleteEntrances();
    }
}