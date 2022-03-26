package com.lovelyn.course_advizor.appointment;

import com.lovelyn.course_advizor.student.Student;
import lombok.*;
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
    PENDING, DECLINED, ACCEPTED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "started_at")
  private LocalDateTime startedAt;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

}
