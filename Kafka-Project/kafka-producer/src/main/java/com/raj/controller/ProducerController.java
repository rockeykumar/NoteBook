package com.raj.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.raj.helper.StudentDataLoader;
import com.raj.kafka.CreateProducer;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.*;
import javax.inject.Inject;

@Controller("/producer")
public class ProducerController {

    @Inject
    StudentDataLoader studentDataLoader;

    @Get(uri = "/insert", produces = "application/json")
    public List<Map<String, Object>> index() {
        ObjectMapper objectMapper = new ObjectMapper();
        String studentData = studentDataLoader.getEntityJsonMappingData();

        List<Map<String, Object>> convertedData = null;
        try {

            convertedData = objectMapper.readValue(studentData, ArrayList.class);
            for(Map<String, Object> mapData : convertedData) {
                String id = ""+mapData.get("id");
                CreateProducer.getInstance().sendMessage(id, mapData, "studentData");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedData;
    }

}
