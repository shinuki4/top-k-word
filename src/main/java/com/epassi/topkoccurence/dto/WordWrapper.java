package com.epassi.topkoccurence.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@ToString
public class WordWrapper implements Serializable {
    @Builder.Default
    private Map<String, Double> wordCheck = Map.of();
    private Integer wordCount;

}
