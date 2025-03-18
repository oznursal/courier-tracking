package com.oznursal.courier.tracking.infra.adapters.config;

import com.oznursal.courier.tracking.domain.service.CourierService;
import com.oznursal.courier.tracking.domain.service.EntranceService;
import com.oznursal.courier.tracking.domain.service.GeoLocationService;
import com.oznursal.courier.tracking.domain.service.StoreService;
import com.oznursal.courier.tracking.infra.adapters.kafka.EventProducer;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.CourierPersistenceAdapter;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.EntrancePersistenceAdapter;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.GeoLocationPersistenceAdapter;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.StorePersistenceAdapter;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.CourierMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.EntranceMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.GeoLocationMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.mapper.StoreMapper;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.CourierRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.EntranceRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.GeoLocationRepository;
import com.oznursal.courier.tracking.infra.adapters.output.persistence.repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CourierMapper courierMapper() {
        return new CourierMapper();
    }

    @Bean
    public EntranceMapper entranceMapper() {
        return new EntranceMapper();
    }

    @Bean
    public GeoLocationMapper geoLocationMapper() {
        return new GeoLocationMapper();
    }

    @Bean
    public StoreMapper storeMapper() {
        return new StoreMapper();
    }

    @Bean
    public CourierPersistenceAdapter courierPersistenceAdapter(CourierRepository courierRepository, GeoLocationRepository geoLocationRepository, CourierMapper courierMapper) {
        return new CourierPersistenceAdapter(courierRepository, geoLocationRepository, courierMapper);
    }

    @Bean
    public CourierService courierService(CourierPersistenceAdapter courierPersistenceAdapter) {
        return new CourierService(courierPersistenceAdapter);
    }

    @Bean
    public EntrancePersistenceAdapter entrancePersistenceAdapter(EntranceRepository entranceRepository,
                                                                 CourierRepository courierRepository,
                                                                 StoreRepository storeRepository,
                                                                 EntranceMapper entranceMapper) {
        return new EntrancePersistenceAdapter(entranceRepository, courierRepository, storeRepository, entranceMapper);
    }

    @Bean
    public EntranceService entranceService(EntrancePersistenceAdapter entrancePersistenceAdapter) {
        return new EntranceService(entrancePersistenceAdapter);
    }

    @Bean
    public StorePersistenceAdapter storePersistenceAdapter(StoreRepository storeRepository, StoreMapper storeMapper) {
        return new StorePersistenceAdapter(storeRepository, storeMapper);
    }

    @Bean
    public StoreService storeService(StorePersistenceAdapter storePersistenceAdapter) {
        return new StoreService(storePersistenceAdapter);
    }

    @Bean
    public GeoLocationPersistenceAdapter geoLocationPersistenceAdapter(GeoLocationRepository geoLocationRepository,
                                                                       CourierRepository courierRepository,
                                                                       EntranceRepository entranceRepository,
                                                                       StoreRepository storeRepository,
                                                                       GeoLocationMapper geoLocationMapper,
                                                                       EventProducer eventProducer) {
        return new GeoLocationPersistenceAdapter(geoLocationRepository, courierRepository, entranceRepository, storeRepository, geoLocationMapper, eventProducer);
    }

    @Bean
    public GeoLocationService geoLocationService(GeoLocationPersistenceAdapter geoLocationPersistenceAdapter) {
        return new GeoLocationService(geoLocationPersistenceAdapter);
    }
}
