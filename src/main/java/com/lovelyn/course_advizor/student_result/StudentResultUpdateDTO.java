package com.lovelyn.course_advizor.student_result;

import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudentResultUpdateDTO {

  @NotNull(message = ValidationErrorCode.GRADE_REQUIRED)
  private StudentResult.Grade grade;

}
