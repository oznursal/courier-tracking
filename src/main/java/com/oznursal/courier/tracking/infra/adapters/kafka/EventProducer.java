package com.oznursal.courier.tracking.infra.adapters.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);
    private static final String GEO_LOCATION_TOPIC = "courier-geo-location";
    private static final String ENTRANCE_TOPIC = "courier-entrance";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendGeoLocationMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(GEO_LOCATION_TOPIC, UUID.randomUUID().toString(), message);
    }

    public void sendEntranceMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(ENTRANCE_TOPIC, UUID.randomUUID().toString(), message);
    }
}