package com.epassi.topkoccurence.dto.enumeration;

import lombok.Getter;

@Getter
public enum TopKErrorCodes {

    UNEXPECTED_EXCEPTION(999, "unexpected exception");

    private final Integer errorCode;
    private final String title;

    TopKErrorCodes(Integer errorCode, String title) {
        this.errorCode = errorCode;
        this.title = title;
    }
}
