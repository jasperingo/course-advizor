package com.lovelyn.course_advizor.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.student.StudentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
public class ReportDTO {

  private Long id;

  @JsonProperty("record_url")
  private String recordUrl;

  private String reply;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class ReportWithStudentDTO extends ReportDTO {

    private StudentDTO student;

  }
}
