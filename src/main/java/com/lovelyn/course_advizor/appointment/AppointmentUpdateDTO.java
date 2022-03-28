package com.lovelyn.course_advizor.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import com.lovelyn.course_advizor.validation.ValidationGroupSequence;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@GroupSequence({AppointmentUpdateDTO.class, ValidationGroupSequence.class})
public class AppointmentUpdateDTO {

  @NotNull(
    message = ValidationErrorCode.STATUS_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  private Appointment.Status status;

  @JsonProperty("started_at")
  @NotNull(
    message = ValidationErrorCode.STARTED_AT_REQUIRED,
    groups = ValidationGroupSequence.ValidationGroupOne.class
  )
  @Future(
    message = ValidationErrorCode.STARTED_AT_PAST,
    groups = ValidationGroupSequence.ValidationGroupTwo.class
  )
  private LocalDateTime startedAt;

}
