package com.example.topkoccurence.service;

import com.example.topkoccurence.dto.WordWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TopKOccurrenceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Read a string and tokenize the content of it
     *
     * @param stream
     * @return A wrapper object containing the map of all word in the stream and their count  and the number of all token in stream
     * @throws IOException in case the stream can't be read
     */
    public WordWrapper countTokenAndWrap(InputStream stream) throws IOException {
        Reader fileReader = new InputStreamReader(stream);
        Map<String, Double> tokenMap = new LinkedHashMap<>();
        int wordCount = 0;
        StreamTokenizer streamTokenizer = new StreamTokenizer(fileReader);
        int currentToken = streamTokenizer.nextToken();
        while (currentToken != StreamTokenizer.TT_EOF) {
            if (streamTokenizer.ttype == StreamTokenizer.TT_WORD) {
                tokenMap.put(streamTokenizer.sval, tokenMap.getOrDefault(streamTokenizer.sval, .0) + 1);
            }
            currentToken = streamTokenizer.nextToken();
            wordCount += 1;
        }
        return WordWrapper.builder()
                .wordCheck(tokenMap)
                .wordCount(wordCount)
                .build();
    }

    /**
     * @param wordLimit limit the number of result in return
     * @param tokenMap  the token to which need to be calulated the frequency and to sort
     * @return List word and their frequency in desc order
     */
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
