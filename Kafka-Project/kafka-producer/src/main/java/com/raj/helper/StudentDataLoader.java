package com.raj.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Singleton
public class StudentDataLoader {

    private static final Logger log = LoggerFactory.getLogger(StudentDataLoader.class);

    @Value("${micronaut.handlers.url}")
    private String studentJsonData;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private  String getFile(String filePath) {
        String textResponse = "";
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        InputStream is = null;

        try {
            is = classPathResource.getInputStream();
            byte[] binaryData = FileCopyUtils.copyToByteArray(is);
            textResponse = new String(binaryData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Exception : ", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("Finally block - IOException from Loading dynamicEntityJSON static file : ", e);
            } catch (Exception e) {
                log.error("Nested Exception from Loading dynamicEntityJSON static file : ", e);
            }
        }

        return textResponse;
    }


    public String getEntityJsonMappingData() {
        String dynamicEntityJSON = getFile(studentJsonData);
        return dynamicEntityJSON;
    }


}
