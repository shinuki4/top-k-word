package com.example.topkoccurence.controller;

import com.example.topkoccurence.dto.WordWrapper;
import com.example.topkoccurence.endpoint.TopKOccurrenceEndpoint;
import com.example.topkoccurence.service.RedisService;
import com.example.topkoccurence.service.TopKOccurrenceService;
import io.lettuce.core.support.caching.RedisCache;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.LinkedHashMap;
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
    public Map<String, Double> generateWordOccurrence(Integer wordLimit, MultipartFile textFile) {
        WordWrapper wordWrapper;
        try {
            String hash = DigestUtils.md5Hex(textFile.getInputStream());
            String redisKey = hash + "_" + wordLimit;
            if (redisService.getFromRedis(redisKey) == null) {
                wordWrapper = topKOccurrenceService.countTokenAndWrap(textFile.getInputStream());
                redisService.saveToRedis(redisKey, wordWrapper);
            } else {
                wordWrapper = (WordWrapper) redisService.getFromRedis(redisKey);
            }
        } catch (IOException e) {
            throw  new ResponseStatusException(HttpStatus.MULTI_STATUS, "unable to parse file", e);
        }

        return topKOccurrenceService.calculateWordOccurrence(wordLimit, wordWrapper);
    }

}
