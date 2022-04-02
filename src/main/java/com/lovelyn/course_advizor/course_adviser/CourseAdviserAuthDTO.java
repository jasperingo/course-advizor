package com.lovelyn.course_advizor.course_adviser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CourseAdviserAuthDTO {

  @NotBlank(message = ValidationErrorCode.PIN_REQUIRED)
  @Size(
    min = 4, max = 4,
    message = ValidationErrorCode.PIN_SIZE
  )
  private String pin;

  @JsonProperty("phone_number")
  @NotBlank(message = ValidationErrorCode.PHONE_NUMBER_REQUIRED)
  @Size(
    max = 11, min = 11,
    message = ValidationErrorCode.PHONE_NUMBER_SIZE
  )
  private String phoneNumber;

}
