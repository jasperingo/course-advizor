package com.lovelyn.course_advizor.course_adviser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CourseAdviserStatisticsDTO {

  @JsonProperty("number_of_students")
  private Long numberOfStudents;

  @JsonProperty("number_of_results")
  private Long numberOfResults;

  @JsonProperty("number_of_appointments")
  private Long numberOfAppointments;

  @JsonProperty("number_of_reports")
  private Long numberOfReports;

  @JsonProperty("number_of_pending_appointments")
  private Long numberOfPendingAppointments;

  @JsonProperty("number_of_pending_reports")
  private Long numberOfPendingReports;

}
