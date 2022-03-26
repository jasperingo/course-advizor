package com.lovelyn.course_advizor.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserDTO;
import com.lovelyn.course_advizor.student_result.StudentResultDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class StudentWithCourseAdviser extends StudentDTO {

    @JsonProperty("course_adviser")
    private CourseAdviserDTO courseAdviser;
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class StudentWithResultDTO extends StudentDTO {

    @JsonProperty("student_result")
    private List<StudentResultDTO> studentResult;
  }
}
