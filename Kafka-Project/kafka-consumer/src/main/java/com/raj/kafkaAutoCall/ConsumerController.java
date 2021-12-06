package com.raj.kafkaAutoCall;

import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Singleton
public class ConsumerController {

    private static KafkaConsumer<String, String> consumer;
    private static String topicName = "studentData";
    private static int pollInterval = 500;


    public ConsumerController() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "studentDataGroup");
        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        this.consumer = new KafkaConsumer<>(props);
    }


//    @Get(uri = "/first", produces = "application/json")
//    public void index() {
//        System.out.println("Consumer Listing...!");
//        List<String> topics = new ArrayList<>();
//        topics.add(topicName);
//        consumer.subscribe(topics);
//        while(true) {
//            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(pollInterval));
//            for (TopicPartition partition : records.partitions()) {
//
//                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
//                for (ConsumerRecord<String, String> record : partitionRecords) {
//                    System.out.println(record);
//                    /// Must be as per IO Thread
////                    exec.submit(new InternalUpdateMessageHandler(record, iodAerospikeWrite));
//                }
//            }
////            commitCompletedMessages();
//
////            System.out.println(records.getClass().getName());
//        }
//    }
//    @PostConstruct
//    public void index() {
//        System.out.println("yes");
//    }

    @EventListener
    public void onStartUp(ServerStartupEvent event) {
        System.out.println("Consumer Listing...! automatically...!");
        List<String> topics = new ArrayList<>();
        topics.add(topicName);
        consumer.subscribe(topics);
        while (1>0) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(pollInterval));
            for (TopicPartition partition : records.partitions()) {
                System.out.println(partition);
//                return;
                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                for (ConsumerRecord<String, String> record : partitionRecords) {
                    System.out.println("Data : " + record.value());
                }
            }
        }
    }


}
