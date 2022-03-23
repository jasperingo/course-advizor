package com.lovelyn.course_advizor.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserDTO;
import lombok.Data;

@Data
public class StudentDTO {

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("matriculation_number")
  private String matriculationNumber;

  @JsonProperty("course_adviser")
  private CourseAdviserDTO courseAdviser;

}
