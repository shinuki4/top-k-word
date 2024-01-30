package com.epassi.topkoccurence.controller;

import com.epassi.topkoccurence.dto.WordWrapper;
import com.epassi.topkoccurence.endpoint.TopKOccurrenceEndpoint;
import com.epassi.topkoccurence.service.RedisService;
import com.epassi.topkoccurence.service.TopKOccurrenceService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class TopKOccurrenceController implements TopKOccurrenceEndpoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TopKOccurrenceService topKOccurrenceService;
    private final RedisService redisService;

    public TopKOccurrenceController(TopKOccurrenceService topKOccurrenceService,
                                    RedisService redisService) {
        this.topKOccurrenceService = topKOccurrenceService;
        this.redisService = redisService;
    }

    @Override
    public ResponseEntity<Map<String,Double>> generateWordOccurrence(Integer wordLimit, MultipartFile textFile) throws IOException {
        WordWrapper wordWrapper;
        if (textFile == null || textFile.isEmpty()) {
            logger.info("File not found or empty");
            return ResponseEntity.notFound().build();
        }
        String redisKey = DigestUtils.md5Hex(textFile.getInputStream());
        if (redisService.getFromRedis(redisKey) == null) {
            logger.info("File ready to get parsed");
            wordWrapper = topKOccurrenceService.countTokenAndWrap(textFile.getInputStream());
            redisService.saveToRedis(redisKey, wordWrapper);
        } else {
            wordWrapper = (WordWrapper) redisService.getFromRedis(redisKey);
            logger.info("File ready from cache: {}", wordWrapper);
        }

        return  ResponseEntity.ok(topKOccurrenceService.calculateWordOccurrence(wordLimit, wordWrapper));
    }

}
