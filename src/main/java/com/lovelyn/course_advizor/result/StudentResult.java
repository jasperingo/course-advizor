package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.student.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name = "student_results")
@NoArgsConstructor
public class StudentResult {

  public enum Grade {
    A, B, C, D, E, F;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Grade grade;

  @ManyToOne
  @JoinColumn(name = "result_id")
  private Result result;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;
}
