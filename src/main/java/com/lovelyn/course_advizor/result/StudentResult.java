package com.lovelyn.course_advizor.result;

import com.fasterxml.jackson.annotation.JsonValue;
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

    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F');

    @JsonValue
    public final Character value;

    Grade(Character value) {
      this.value = value;
    }
  }

  @Id
  @Column(nullable = false)
  private Long id;

  private Grade grade;

  @ManyToOne
  @JoinColumn(name = "result_id")
  private Result result;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;
}
