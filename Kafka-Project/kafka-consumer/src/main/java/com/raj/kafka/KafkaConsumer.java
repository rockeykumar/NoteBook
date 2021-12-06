//package com.raj.kafka;
//
//
//import io.micronaut.configuration.kafka.annotation.KafkaKey;
//import io.micronaut.configuration.kafka.annotation.KafkaListener;
//import io.micronaut.configuration.kafka.annotation.OffsetReset;
//import io.micronaut.configuration.kafka.annotation.Topic;
//import io.micronaut.runtime.event.annotation.EventListener;
//
//@KafkaListener(offsetReset = OffsetReset.EARLIEST)
//public class KafkaConsumer {
//
//
//    @Topic("studentData")
//    @EventListener
//    public void receive(@KafkaKey String brand, String name) {
//        System.out.println("Got Product - " + name + " by " + brand);
//    }
//}
