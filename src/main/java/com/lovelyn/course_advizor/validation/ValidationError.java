package com.lovelyn.course_advizor.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ValidationError {

    String name;

    String message;

    @JsonProperty("error_code")
    String errorCode;

    Object value;

    Integer index;

}
