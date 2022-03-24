package com.lovelyn.course_advizor.course_adviser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CourseAdviserAuthDTO {

  @NotBlank(
    message = "pin / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Pin is required"
  )
  @Size(
    min = 4,
    max = 4,
    message = "pin / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Pin must be {max} characters long"
  )
  private String pin;

  @JsonProperty("phone_number")
  @NotBlank(
    message = "phone_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Phone number is required"
  )
  @Size(
    max = 11,
    min = 11,
    message = "phone_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Phone number must be {max} characters long"
  )
  private String phoneNumber;

}
