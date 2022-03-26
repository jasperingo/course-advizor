package com.lovelyn.course_advizor.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserIdExists;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import com.lovelyn.course_advizor.validation.ValidationGroupSequence;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;

@Data
@GroupSequence({StudentCreateDTO.class, ValidationGroupSequence.class})
public class StudentCreateDTO {

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

  @JsonProperty("matriculation_number")
  @NotBlank(
    message = ValidationErrorCode.MATRICULATION_NUMBER_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Size(
    max = 11,
    min = 11,
    message = ValidationErrorCode.MATRICULATION_NUMBER_SIZE,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @StudentMatriculationNumberAlreadyExists(
    message = ValidationErrorCode.MATRICULATION_NUMBER_EXISTS,
    groups = ValidationGroupSequence.ValidationGroupFour.class
  )
  private String matriculationNumber;

  @JsonProperty("course_adviser_id")
  @NotNull(
    message = ValidationErrorCode.COURSE_ADVISER_ID_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = ValidationErrorCode.COURSE_ADVISER_ID_MIN,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @CourseAdviserIdExists(
    message = ValidationErrorCode.COURSE_ADVISER_ID_DO_NOT_EXIST,
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  private Long courseAdviserId;

}
