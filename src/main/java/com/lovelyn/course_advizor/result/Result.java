package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import com.lovelyn.course_advizor.session.Session;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
@Table(name = "results")
@NoArgsConstructor
public class Result {

  public enum Semester {

    FIRST("first"),
    SECOND("second");

    public final String value;

    Semester(String value) {
      this.value = value;
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long id;

  @Column(name = "course_code")
  private String courseCode;

  @Enumerated(EnumType.STRING)
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

}
