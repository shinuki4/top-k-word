package com.epassi.topkoccurence.integration;

import com.epassi.topkoccurence.controller.TopKOccurrenceController;
import com.epassi.topkoccurence.dto.WordWrapper;
import com.epassi.topkoccurence.service.RedisService;
import com.epassi.topkoccurence.service.TopKOccurrenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStream;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(TopKOccurrenceController.class)
@AutoConfigureMockMvc
public class TopKOccurrenceApiTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopKOccurrenceService topKOccurrenceService;

    @MockBean
    private RedisService redisService;


    @Test
    void generateWordOccurrence_shouldReturnWordOccurrences2() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("textFile", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test content Test content Test content Test content Test content Test content Test content Test content Test content".getBytes());
        Integer wordLimit = 1;

        Map<String, Double> expectedResult = Map.of("word1", 3.0, "word2", 2.0);

        WordWrapper mockedWordWrapper = WordWrapper.builder()
                .wordCount(expectedResult.size())
                .wordCheck(expectedResult)
                .build();

        // Mocking service method to return the mockedWordWrapper
        doReturn(mockedWordWrapper).when(topKOccurrenceService).countTokenAndWrap(any(InputStream.class));

        // Mocking service method to return the expectedResult
        when(topKOccurrenceService.calculateWordOccurrence(eq(wordLimit), any(WordWrapper.class))).thenReturn(expectedResult);



        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/top-k-occurrence/{word-limit}", wordLimit)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.word1").value(3.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.word2").value(2.0));
    }

    @Test
    void generateWordOccurrence_shouldReturnNotFoundWhenFileIsEmpty() throws Exception {
        // Arrange
        MockMultipartFile emptyFile = new MockMultipartFile("textFile", "", MediaType.TEXT_PLAIN_VALUE, "".getBytes());
        Integer wordLimit = 10;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/top-k-occurrence/{word-limit}", wordLimit)
                        .file(emptyFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
