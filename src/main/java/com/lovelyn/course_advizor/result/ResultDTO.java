package com.lovelyn.course_advizor.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.session.SessionDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultDTO {

  private Long id;

  @JsonProperty("course_code")
  private String courseCode;

  private Result.Semester semester;

  @JsonProperty("created_at")
  private LocalDateTime createAt;

  private SessionDTO session;
}
