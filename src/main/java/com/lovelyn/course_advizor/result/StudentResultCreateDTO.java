package com.lovelyn.course_advizor.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.student.StudentIdExists;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import com.lovelyn.course_advizor.validation.ValidationGroupSequence;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@GroupSequence({StudentResultCreateDTO.class, ValidationGroupSequence.class})
public class StudentResultCreateDTO {

  @NotNull(
    message = ValidationErrorCode.GRADE_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  private StudentResult.Grade grade;

  @JsonProperty("student_id")
  @NotNull(
    message = ValidationErrorCode.STUDENT_ID_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = ValidationErrorCode.STUDENT_ID_MIN,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @StudentIdExists(
    message = ValidationErrorCode.STUDENT_ID_DO_NOT_EXIST,
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  private Long studentId;

  @JsonProperty("result_id")
  @NotNull(
    message = ValidationErrorCode.RESULT_ID_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Min(
    value = 1,
    message = ValidationErrorCode.RESULT_ID_MIN,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @ResultIdExists(
    message = ValidationErrorCode.RESULT_ID_DO_NOT_EXIST,
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  private Long resultId;

}
