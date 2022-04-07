package com.lovelyn.course_advizor.call;

import com.lovelyn.course_advizor.appointment.Appointment;
import com.lovelyn.course_advizor.report.Report;
import com.lovelyn.course_advizor.session.Session;
import com.lovelyn.course_advizor.student.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "calls")
public class Call {

  public enum Status { ACTIVE, INACTIVE }

  public enum Action {
    RESULT(1, "one"),
    APPOINTMENT(2, "two"),
    REPORT(3, "three");

    public final Integer number;

    public final String word;

    Action(Integer number, String word) {
      this.number = number;
      this.word = word;
    }

    public static Action fromString(final String value) {

      final Integer number = Integer.valueOf(value);

      if (Objects.equals(number, RESULT.number))
        return RESULT;

      if (Objects.equals(number, APPOINTMENT.number))
        return APPOINTMENT;

      if (Objects.equals(number, REPORT.number))
        return REPORT;

      return null;
    }
  }

  public enum CallDirection { Inbound, Outbound }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "call_session_id")
  private String callSessionId;

  @Convert(converter = StatusConverter.class)
  private Status status;

  @Convert(converter = ActionConverter.class)
  private Action action;

  @Enumerated(EnumType.STRING)
  @Column(name = "call_direction")
  private CallDirection callDirection;

  private Long duration;

  private Double cost;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private Session session;

  @OneToOne
  @JoinColumn(name = "appointment_id")
  private Appointment appointment;

  @OneToOne
  @JoinColumn(name = "report_id")
  private Report report;

  public static class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status attribute) {
      return attribute.name().toLowerCase();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
      if (dbData.equals(Status.ACTIVE.name().toLowerCase()))
        return Status.ACTIVE;

      if (dbData.equals(Status.INACTIVE.name().toLowerCase()))
        return Status.INACTIVE;

      return null;
    }
  }

  public static class ActionConverter implements AttributeConverter<Action, String> {

    @Override
    public String convertToDatabaseColumn(Action attribute) {
      return attribute != null ? attribute.name().toLowerCase() : null;
    }

    @Override
    public Action convertToEntityAttribute(String dbData) {
      if (dbData != null) {
        if (dbData.equals(Action.RESULT.name().toLowerCase()))
          return Action.RESULT;

        if (dbData.equals(Action.APPOINTMENT.name().toLowerCase()))
          return Action.APPOINTMENT;

        if (dbData.equals(Action.REPORT.name().toLowerCase()))
          return Action.REPORT;
      }

      return null;
    }
  }

}
