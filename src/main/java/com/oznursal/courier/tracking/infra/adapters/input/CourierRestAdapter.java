package com.oznursal.courier.tracking.infra.adapters.input;

import com.oznursal.courier.tracking.application.ports.input.courier.CreateCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.DeleteCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.GetCourierUseCase;
import com.oznursal.courier.tracking.application.ports.input.courier.UpdateCourierUseCase;
import com.oznursal.courier.tracking.domain.model.Courier;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.request.CourierRequest;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.response.CourierResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/couriers")
@RequiredArgsConstructor
public class CourierRestAdapter {

    private final GetCourierUseCase getCourierUseCase;

    private final CreateCourierUseCase createCourierUseCase;

    private final UpdateCourierUseCase updateCourierUseCase;

    private final DeleteCourierUseCase deleteCourierUseCase;

    private final ModelMapper mapper;

    @PostMapping
    ResponseEntity<CourierResponse> createCourier(@RequestBody @Valid CourierRequest courierToCreate) {
        Courier courier = mapper.map(courierToCreate, Courier.class);
        courier = createCourierUseCase.createCourier(courier);
        return new ResponseEntity<>(mapper.map(courier, CourierResponse.class), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CourierResponse> getCourier(@PathVariable Long id) {
        Courier courier = getCourierUseCase.getCourier(id);
        return new ResponseEntity<>(mapper.map(courier, CourierResponse.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourierResponse>> getCouriers() {
        List<Courier> couriers = getCourierUseCase.getCouriers();
        List<CourierResponse> courierResponses = couriers.stream()
                .map(element -> mapper.map(element, CourierResponse.class)).collect(Collectors.toList());

        return new ResponseEntity<>(courierResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/total-distances")
    public ResponseEntity<Double> getCourierTotalDistance(@PathVariable Long id) {
        return new ResponseEntity<>(getCourierUseCase.getTotalDistances(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CourierResponse> updateCourier(@PathVariable("id") Long id, @RequestBody @Valid CourierRequest courierToUpdate) {
        Courier courier = mapper.map(courierToUpdate, Courier.class);
        courier = updateCourierUseCase.updateCourier(id, courier);
        return new ResponseEntity<>(mapper.map(courier, CourierResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteCourier(@PathVariable("id") Long id) {
        deleteCourierUseCase.deleteCourier(id);
    }

    @DeleteMapping()
    public void deleteCouriers() {
        deleteCourierUseCase.deleteCouriers();
    }
}