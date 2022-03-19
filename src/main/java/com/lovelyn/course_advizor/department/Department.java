package com.lovelyn.course_advizor.department;

import java.time.LocalDateTime;

import javax.persistence.Entity;

@Entity
public class Department {
  
  private Long id;

  private String name;

  private String abbreviation;
  
  private LocalDateTime createdAt;
  
}
