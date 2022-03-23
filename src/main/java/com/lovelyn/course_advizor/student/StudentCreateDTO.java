package com.lovelyn.course_advizor.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.exception.ValidationErrorCode;
import com.lovelyn.course_advizor.validation.ValidationGroupOne;
import com.lovelyn.course_advizor.validation.ValidationGroupSequence;
import com.lovelyn.course_advizor.validation.ValidationGroupTwo;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@GroupSequence({StudentCreateDTO.class, ValidationGroupSequence.class})
public class StudentCreateDTO {

  @JsonProperty("first_name")
  @NotBlank(
    message = "first_name / "+
      ValidationErrorCode.FIELD_INVALID +
      " / First name is invalid",
    groups = ValidationGroupOne.class
  )
  private String firstName;

  @JsonProperty("last_name")
  @NotBlank(
    message = "last_name / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Last name is invalid",
    groups = ValidationGroupOne.class
  )
  private String lastName;

  @JsonProperty("matriculation_number")
  @NotBlank(
    message = "matriculation_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Matriculation number is required",
    groups = ValidationGroupOne.class
  )
  @Size(
    max = 11,
    min = 11,
    message = "matriculation_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Matriculation number must be {max} characters long",
    groups = ValidationGroupTwo.class
  )
  private String matriculationNumber;

  @JsonProperty("course_adviser_id")
  @NotNull(
    message = "course_adviser_id / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Course adviser id is invalid",
    groups = ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = "course_adviser_id / "+
      ValidationErrorCode.ID_INVALID +
      " / Course adviser id cannot be less than one",
    groups = ValidationGroupTwo.class
  )
  private Long courseAdviserId;

}
