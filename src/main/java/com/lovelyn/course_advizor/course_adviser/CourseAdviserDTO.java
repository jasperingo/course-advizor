package com.lovelyn.course_advizor.course_adviser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovelyn.course_advizor.department.DepartmentDTO;
import com.lovelyn.course_advizor.session.SessionDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CourseAdviserDTO {

    private Long id;

    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private DepartmentDTO department;

    private SessionDTO session;
  
}
