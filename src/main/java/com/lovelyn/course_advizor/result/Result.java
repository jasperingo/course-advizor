package com.lovelyn.course_advizor.result;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import com.lovelyn.course_advizor.session.Session;
import com.lovelyn.course_advizor.student_result.StudentResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ToString
@Entity
@Table(name = "results")
@NoArgsConstructor
public class Result {

  public enum Semester {

    FIRST("1"),
    SECOND("2");

    @JsonValue
    public String getName() {
      return name().toLowerCase();
    }

    public final String number;

    Semester(String number) {
      this.number = number;
    }

    public static Semester fromString(final String value) {

      if (Objects.equals(value, SECOND.number))
        return SECOND;

      if (Objects.equals(value, FIRST.number))
        return FIRST;

      return null;
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long id;

  @Column(name = "course_code")
  private String courseCode;

  @Convert(converter = SemesterConverter.class)
  private Semester semester;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createAt;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private Session session;

  @ManyToOne
  @JoinColumn(name = "course_adviser_id")
  private CourseAdviser courseAdviser;

  @OneToMany(mappedBy = "result")
  @ToString.Exclude
  private List<StudentResult> studentResult;

  public static class SemesterConverter implements AttributeConverter<Semester, String> {

    @Override
    public String convertToDatabaseColumn(Semester attribute) {
      return attribute.getName();
    }

    @Override
    public Semester convertToEntityAttribute(String dbData) {
      if (dbData.equals(Semester.FIRST.getName()))
          return Semester.FIRST;

      if (dbData.equals(Semester.SECOND.getName()))
        return Semester.SECOND;

      return null;
    }
  }

}
