package com.epassi.topkoccurence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    protected String serviceName;
    protected Integer errorCode;
    protected String message;
    protected String cause;
    protected String causeDetails;
    protected ErrorResponse errorResponse;
}
