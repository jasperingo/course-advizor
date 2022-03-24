package com.lovelyn.course_advizor.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.student.StudentIdExists;
import com.lovelyn.course_advizor.validation.ValidationGroupSequence;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import com.lovelyn.course_advizor.session.SessionIdExists;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@GroupSequence({ResultCreateDTO.class, ValidationGroupSequence.class})
public class ResultCreateDTO {

  @JsonProperty("course_code")
  @NotBlank(
    message = ValidationErrorCode.COURSE_CODE_BLANK,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Size(
    max = 6, min = 6,
    message = ValidationErrorCode.COURSE_CODE_LENGTH,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  @Pattern(
    regexp = "^[A-Z]{3}[0-9]{3}$",
    message = ValidationErrorCode.COURSE_CODE_PATTERN,
    groups = ValidationGroupSequence.ValidationGroupThree.class
  )
  private String courseCode;

  @NotNull(
    message = ValidationErrorCode.SEMESTER_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  private Result.Semester semester;

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
  private Long sectionId;

  @Valid
  @JsonProperty("student_results")
  @NotEmpty(
    message = ValidationErrorCode.STUDENT_RESULTS_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  private List<StudentResultCreateDTO> studentResultCreateDTO;

  @Data
  @GroupSequence({StudentResultCreateDTO.class, ValidationGroupSequence.class})
  public static class StudentResultCreateDTO {

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

  }
}
