package com.lovelyn.course_advizor.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserDTO;
import com.lovelyn.course_advizor.result.StudentResultDTO;
import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {

  private Long id;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("matriculation_number")
  private String matriculationNumber;

  @JsonProperty("course_adviser")
  private CourseAdviserDTO courseAdviser;

  @Data
  public static class StudentWithResultDTO {

    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("matriculation_number")
    private String matriculationNumber;

    @JsonProperty("student_result")
    private List<StudentResultDTO.StudentResultGradeDTO> studentResult;
  }
}
