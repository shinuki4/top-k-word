package com.example.topkoccurence.service;

import com.example.topkoccurence.dto.WordWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TopKOccurrenceService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public Map<String, Double> calculateWordOccurrence(Integer wordLimit, WordWrapper tokenMap) {
        return tokenMap.getWordCheck()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(wordLimit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        it -> it.getValue() / tokenMap.getWordCount(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

    }
}
