package com.lovelyn.course_advizor.student_result;

import com.lovelyn.course_advizor.result.ResultDTO;
import com.lovelyn.course_advizor.student.StudentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class StudentResultDTO {

  private Long id;

  private StudentResult.Grade grade;

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class StudentResultWithResultAndStudentDTO extends StudentResultDTO {

    private ResultDTO result;

    private StudentDTO student;

  }
}
