package com.lovelyn.course_advizor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
public class ResponseDTO<T> {

    public enum Status {
        SUCCESS, ERROR
    }

    Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;
}
