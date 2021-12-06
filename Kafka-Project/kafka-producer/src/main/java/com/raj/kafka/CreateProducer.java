package com.raj.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raj.helper.StudentDataLoader;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.context.annotation.Factory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Properties;


@Factory
public class CreateProducer {

    private static final Logger logger = LoggerFactory.getLogger(StudentDataLoader.class);

    private KafkaProducer<String, String> producer;
    private static volatile CreateProducer instance;

    private CreateProducer() {

        Properties props = new Properties();

//        for consumer only
//        props.put(CommonConstants.KAFKA_COMMON_SERVER_KEY, CommonConstants.KAFKA_COMMON_SERVER_VALUE);
//        "group.id"                               swimlane-service-consumer
//        props.put(SwimlaneConstants.GROUP_ID, SwimlaneConstants.KAFKA_GROUP_ID);
//        props.put(SwimlaneConstants.ENABLE_AUTO_COMMIT, SwimlaneConstants.KAFKA_ENABLE_AUTO_COMMIT);
//        props.put(SwimlaneConstants.AUTO_OFFSET_RESET, SwimlaneConstants.KAFKA_AUTO_OFFSET_RESET);
//        props.put(SwimlaneConstants.KEY_DESERIALIZER, StringDeserializer.class.getName());
//        props.put(SwimlaneConstants.VALUE_DESERIALIZER, StringDeserializer.class.getName());
//        props.put(SwimlaneConstants.MAX_POLL_RECORDS, SwimlaneConstants.KAFKA_MAX_POLL_RECORDS);


        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("group.id", "studentData_group"); //for consumer only
        producer = new KafkaProducer<>(props);
    }


    /**
     * @return singleton object of this class
     */
    public static CreateProducer getInstance() {

        if (instance == null) {
            synchronized (CreateProducer.class) {
                if (instance == null)
                    instance = new CreateProducer();
            }
        }
        return instance;
    }

    /**
     * @return getInstance and  Make singleton from serialize and deserialize operation.
     */
    protected CreateProducer readResolve() {
        return getInstance();
    }



    //    key = id, messagePayload = payLoad
    @Singleton
    public void sendMessage(@KafkaKey String key, Map<String, Object> messagePayload, String topicName) {

        logger.info("msg sent to internal update  kafka topic from loan operation kafka producer messagePayload = {} ",  messagePayload);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messagePayLoadString = objectMapper.writeValueAsString(messagePayload);
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName, key, messagePayLoadString);
            producer.send(record);

        } catch (Exception e) {

            logger.error("Exception occurred during sending payload to internal update service from loan operation service ",  e);
        }
    }

//    key = id, messagePayload = payLoad
//    @Singleton
//    public void sendMessage(@KafkaKey String key, String messagePayload, String topicName) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//
//            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, messagePayload);
//            producer.send(record);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//    }
}
