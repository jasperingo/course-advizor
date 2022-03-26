package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.student.StudentDTO;
import lombok.Data;

@Data
public class StudentResultDTO {

  private Long id;

  private StudentResult.Grade grade;

  private ResultDTO result;

  private StudentDTO student;

  @Data
  public static class StudentResultGradeDTO {

    private Long id;

    private StudentResult.Grade grade;

  }
}
