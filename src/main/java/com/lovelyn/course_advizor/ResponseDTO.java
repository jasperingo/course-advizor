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

    public static <E> ResponseDTO<E> success(final String message, final E data) {
        return new ResponseDTO<>(Status.SUCCESS, message, data);
    }

    public static <E> ResponseDTO<E> error(final String message, final E data) {
        return new ResponseDTO<>(Status.ERROR, message, data);
    }
}
