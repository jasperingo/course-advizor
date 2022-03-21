package com.lovelyn.course_advizor.department;

import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "departments")
@NoArgsConstructor
public class Department {
  
  @Id
  private Long id;

  private String name;

  private String abbreviation;
  
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "department")
  @ToString.Exclude
  private List<CourseAdviser> courseAdvisers;

}
