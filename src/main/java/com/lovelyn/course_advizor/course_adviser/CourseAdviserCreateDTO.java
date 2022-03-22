package com.lovelyn.course_advizor.course_adviser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.department.DepartmentIdExists;
import com.lovelyn.course_advizor.exception.ValidationErrorCode;
import com.lovelyn.course_advizor.section.SectionIdExists;
import com.lovelyn.course_advizor.validation.ValidationGroupOne;
import com.lovelyn.course_advizor.validation.ValidationGroupSequence;
import com.lovelyn.course_advizor.validation.ValidationGroupThree;
import com.lovelyn.course_advizor.validation.ValidationGroupTwo;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;

@Data
@GroupSequence({CourseAdviserCreateDTO.class, ValidationGroupSequence.class})
public class CourseAdviserCreateDTO {

  @JsonProperty("first_name")
  @NotEmpty(
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

  @Size(
    min = 4,
    max = 4,
    message = "pin / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Pin must be {max} characters long",
    groups = ValidationGroupOne.class
  )
  private String pin;

  @JsonProperty("phone_number")
  @Size(
    max = 11,
    min = 11,
    message = "phone_number / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Phone number must be {max} characters long",
    groups = ValidationGroupOne.class
  )
  @CourseAdviserPhoneNumberAlreadyExists(
    message = "phone_number / "+
      ValidationErrorCode.FIELD_EXISTS +
      " / Phone number already exists",
    groups = ValidationGroupTwo.class
  )
  private String phoneNumber;

  @JsonProperty("department_id")
  @NotNull(
    message = "department_id / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Department id is invalid",
    groups = ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = "department_id / "+
      ValidationErrorCode.ID_INVALID +
      " / Department id cannot be less than one",
    groups = ValidationGroupTwo.class
  )
  @DepartmentIdExists(
    message = "department_id / "+
      ValidationErrorCode.ID_INVALID +
      " / Department with id ${validatedValue} do not exist",
    groups = ValidationGroupThree.class
  )
  private Long departmentId;

  @JsonProperty("section_id")
  @NotNull(
    message = "section_id / "+
      ValidationErrorCode.FIELD_INVALID +
      " / Section id is invalid",
    groups = ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = "section_id / "+
      ValidationErrorCode.ID_INVALID +
      " / Section id cannot be less than one",
    groups = ValidationGroupTwo.class
  )
  @SectionIdExists(
    message = "section_id / "+
      ValidationErrorCode.ID_INVALID +
      " / Section with id ${validatedValue} do not exist",
    groups = ValidationGroupThree.class
  )
  private Long sectionId;

}
