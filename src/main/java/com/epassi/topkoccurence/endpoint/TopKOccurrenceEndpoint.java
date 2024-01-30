package com.epassi.topkoccurence.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.io.IOException;
import java.util.Map;

@HttpExchange("top-k-occurrence")
public interface TopKOccurrenceEndpoint {

    @PostExchange("{word-limit}")
    ResponseEntity<Map<String,Double>> generateWordOccurrence(@PathVariable("word-limit") Integer wordLimit, @RequestBody MultipartFile textFile) throws IOException;
}
