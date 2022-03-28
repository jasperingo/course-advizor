package com.lovelyn.course_advizor.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.session.SessionIdExists;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import com.lovelyn.course_advizor.validation.ValidationGroupSequence;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;

@Data
@ResultWithCourseCodeSessionAndSemesterAlreadyExists(
  message = ValidationErrorCode.RESULT_WITH_COURSE_CODE_SESSION_ID_AND_SEMESTER_EXISTS,
  groups = ValidationGroupSequence.ValidationGroupFour.class
)
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
  private Long sessionId;

}
