package com.lovelyn.course_advizor.section;

import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "sections")
@NoArgsConstructor
public class Section {
  
  @Id
  private Long id;

  @Column(name = "started_at")
  private Integer startedAt;

  @Column(name = "ended_at")
  private Integer endedAt;

  @OneToMany(mappedBy = "section")
  @ToString.Exclude
  private List<CourseAdviser> courseAdvisers;

}
