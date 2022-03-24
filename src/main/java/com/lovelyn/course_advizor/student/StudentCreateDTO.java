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
  @NotBlank(
    message = "first_name / "+
      ValidationErrorCode.FIELD_INVALID +
      " / First name is invalid",
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  private String firstName;

  @JsonProperty("last_name")
  @NotBlank(
    message = "last_name / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Last name is invalid",
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  private String lastName;

  @JsonProperty("matriculation_number")
  @NotBlank(
    message = "matriculation_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Matriculation number is required",
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Size(
    max = 11,
    min = 11,
    message = "matriculation_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Matriculation number must be {max} characters long",
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @Pattern(
    regexp = "\\d+",
    message = "matriculation_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Matriculation number is invalid",
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  @StudentMatriculationNumberAlreadyExists(
    message = "matriculation_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Matriculation number already exists",
    groups = ValidationGroupSequence.ValidationGroupFour.class
  )
  private String matriculationNumber;

  @JsonProperty("course_adviser_id")
  @NotNull(
    message = "course_adviser_id / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Course adviser id is invalid",
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = "course_adviser_id / "+
      ValidationErrorCode.ID_INVALID +
      " / Course adviser id cannot be less than one",
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @CourseAdviserIdExists(
    message = "course_adviser_id / "+
      ValidationErrorCode.ID_INVALID +
      " / Course adviser with id ${validatedValue} do not exist",
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  private Long courseAdviserId;

}
