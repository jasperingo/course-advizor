package com.lovelyn.course_advizor.session;

import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import com.lovelyn.course_advizor.result.Result;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "sessions")
@NoArgsConstructor
public class Session {
  
  @Id
  private Long id;

  @Column(name = "started_at")
  private Integer startedAt;

  @Column(name = "ended_at")
  private Integer endedAt;

  @OneToMany(mappedBy = "session")
  @ToString.Exclude
  private List<CourseAdviser> courseAdvisers;

  @OneToMany(mappedBy = "session")
  @ToString.Exclude
  private List<Result> results;

}
