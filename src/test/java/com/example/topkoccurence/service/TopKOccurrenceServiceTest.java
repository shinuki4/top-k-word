package com.example.topkoccurence.service;

import com.example.topkoccurence.dto.WordWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TopKOccurrenceServiceTest {
    @InjectMocks
    private TopKOccurrenceService topKOccurrenceService;

    @Test
    void testCountTokenAndWrap() throws IOException {
        // Prepare test data
        String inputString = "This is a sample input string. This is a sample input string.";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());

        // Call the method to be tested
        WordWrapper wordWrapper = topKOccurrenceService.countTokenAndWrap(inputStream);

        // Verify the result
        Map<String, Double> wordCheck = wordWrapper.getWordCheck();
        assertEquals(12, wordWrapper.getWordCount());
        assertEquals(2, wordCheck.get("This"));
        assertEquals(2, wordCheck.get("is"));
        assertEquals(2, wordCheck.get("a"));
        assertEquals(2, wordCheck.get("sample"));
        assertEquals(2, wordCheck.get("input"));
        assertEquals(2, wordCheck.get("string."));
    }

    @Test
    void testCalculateWordOccurrence() {
        // Prepare test data
        WordWrapper wordWrapper = WordWrapper.builder()
                .wordCheck(Map.of("This", 5.0, "is", 4.0, "a", 3.0, "sample", 2.0, "input", 2.0, "string", 2.0))
                .wordCount(12)
                .build();

        // Call the method to be tested
        Map<String, Double> result = topKOccurrenceService.calculateWordOccurrence(3, wordWrapper);

        // Verify the result
        assertEquals(3, result.size());
        assertEquals(0.4166666666666667, result.get("This"));
        assertEquals(0.3333333333333333, result.get("is"));
        assertEquals(0.25, result.get("a"));
    }
}