package com.lovelyn.course_advizor.course_adviser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="course_advisers")
public class CourseAdviser {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Long id;

  @Column(name="first_name")
  @JsonProperty("first_name")
  public String firstName;

  @Column(name="last_name")
  @JsonProperty("last_name")
  public String lastName;

  public String pin;

  @Column(name="phone_number")
  @JsonProperty("phone_number")
  public String phoneNumber;

  public String section;
  
}
