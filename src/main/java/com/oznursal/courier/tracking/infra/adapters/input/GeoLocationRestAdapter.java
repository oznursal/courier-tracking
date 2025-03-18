package com.oznursal.courier.tracking.infra.adapters.input;

import com.oznursal.courier.tracking.application.ports.input.geolocation.CreateGeoLocationUseCase;
import com.oznursal.courier.tracking.application.ports.input.geolocation.GetGeoLocationUseCase;
import com.oznursal.courier.tracking.domain.model.GeoLocation;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.request.GeoLocationRequest;
import com.oznursal.courier.tracking.infra.adapters.input.rest.data.response.GeoLocationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/geo-locations")
@RequiredArgsConstructor
public class GeoLocationRestAdapter {

    private final CreateGeoLocationUseCase createGeoLocationUseCase;

    private final GetGeoLocationUseCase getGeoLocationUseCase;

    private final ModelMapper mapper;

    @PostMapping
    ResponseEntity<GeoLocationResponse> createGeoLocations(@RequestBody @Valid GeoLocationRequest geoLocationRequest) {
        GeoLocation geoLocation = mapper.map(geoLocationRequest, GeoLocation.class);
        geoLocation = createGeoLocationUseCase.createGeoLocation(geoLocation);
        return new ResponseEntity<>(mapper.map(geoLocation, GeoLocationResponse.class), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GeoLocationResponse> getGeoLocation(@PathVariable Long id) {
        GeoLocation geoLocation = getGeoLocationUseCase.getGeoLocation(id);
        return new ResponseEntity<>(mapper.map(geoLocation, GeoLocationResponse.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GeoLocationResponse>> getGeoLocations() {
        List<GeoLocation> geoLocations = getGeoLocationUseCase.getGeoLocations();
        List<GeoLocationResponse> geoLocationResponses = geoLocations.stream()
                .map(element -> mapper.map(element, GeoLocationResponse.class)).collect(Collectors.toList());

        return new ResponseEntity<>(geoLocationResponses, HttpStatus.OK);
    }
}
