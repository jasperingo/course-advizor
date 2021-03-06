package com.lovelyn.course_advizor.course_adviser;

import com.lovelyn.course_advizor.department.Department;
import com.lovelyn.course_advizor.result.Result;
import com.lovelyn.course_advizor.session.Session;
import com.lovelyn.course_advizor.student.Student;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name="course_advisers")
public class CourseAdviser {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @Column(name="first_name")
  private String firstName;

  @Column(name="last_name")
  private String lastName;

  private String pin;

  @Column(name="phone_number", unique = true)
  private String phoneNumber;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private Session session;

  @OneToMany(mappedBy = "courseAdviser")
  @ToString.Exclude
  private List<Student> students;

  @OneToMany(mappedBy = "courseAdviser")
  @ToString.Exclude
  private List<Result> results;

}
