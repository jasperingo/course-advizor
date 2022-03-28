package com.lovelyn.course_advizor.appointment;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lovelyn.course_advizor.student.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

  public enum Status {
    PENDING, DECLINED, ACCEPTED;

    @JsonValue
    public String getName() {
      return name().toLowerCase();
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long id;

  @Convert(converter = StatusConverter.class)
  private Status status;

  @Column(name = "started_at")
  private LocalDateTime startedAt;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  public static class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status attribute) {
      return attribute.getName();
    }

    @Override
    public Status convertToEntityAttribute(String status) {
      if (status.equals(Appointment.Status.PENDING.getName()))
        return Appointment.Status.PENDING;

      if (status.equals(Appointment.Status.ACCEPTED.getName()))
        return Appointment.Status.ACCEPTED;

      if (status.equals(Appointment.Status.DECLINED.getName()))
        return Appointment.Status.DECLINED;

      return null;
    }
  }

}
