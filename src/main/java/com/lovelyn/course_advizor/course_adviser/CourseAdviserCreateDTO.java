package com.lovelyn.course_advizor.course_adviser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.department.DepartmentIdExists;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import com.lovelyn.course_advizor.session.SessionIdExists;
import com.lovelyn.course_advizor.validation.ValidationGroupSequence;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;

@Data
@GroupSequence({CourseAdviserCreateDTO.class, ValidationGroupSequence.class})
public class CourseAdviserCreateDTO {

  @JsonProperty("first_name")
  @NotEmpty(
    message = ValidationErrorCode.FIRST_NAME_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  private String firstName;

  @JsonProperty("last_name")
  @NotBlank(
    message = ValidationErrorCode.LAST_NAME_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  private String lastName;

  @NotBlank(
    message = ValidationErrorCode.PIN_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Size(
    min = 4,
    max = 4,
    message = ValidationErrorCode.PIN_SIZE,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  private String pin;

  @JsonProperty("phone_number")
  @NotBlank(
    message = ValidationErrorCode.PHONE_NUMBER_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Size(
    max = 11,
    min = 11,
    message = ValidationErrorCode.PHONE_NUMBER_SIZE,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @CourseAdviserPhoneNumberAlreadyExists(
    message = ValidationErrorCode.PHONE_NUMBER_EXISTS,
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  private String phoneNumber;

  @JsonProperty("department_id")
  @NotNull(
    message = ValidationErrorCode.DEPARTMENT_ID_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = ValidationErrorCode.DEPARTMENT_ID_MIN,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @DepartmentIdExists(
    message = ValidationErrorCode.DEPARTMENT_ID_DO_NOT_EXIST,
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  private Long departmentId;

  @JsonProperty("session_id")
  @NotNull(
    message = ValidationErrorCode.SESSION_ID_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = ValidationErrorCode.SESSION_ID_MIN,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @SessionIdExists(
    message = ValidationErrorCode.SESSION_ID_DO_NOT_EXIST,
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  private Long sessionId;

}
