package com.example.topkoccurence.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class WordWrapper implements Serializable {
    private Map<String, Double> wordCheck;
    private Integer wordCount;

}
