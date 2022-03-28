package com.lovelyn.course_advizor.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.student.StudentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

  private Long id;

  private Appointment.Status status;

  @JsonProperty("started_at")
  private LocalDateTime startedAt;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class AppointmentWithStudentDTO extends AppointmentDTO {

    private StudentDTO student;

  }

}
