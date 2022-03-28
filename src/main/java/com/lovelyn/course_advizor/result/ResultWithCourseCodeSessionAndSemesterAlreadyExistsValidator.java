package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserAuthenticationFilter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

public class ResultWithCourseCodeSessionAndSemesterAlreadyExistsValidator
  implements ConstraintValidator<ResultWithCourseCodeSessionAndSemesterAlreadyExists, ResultCreateDTO> {

  @Autowired
  @Setter
  private ResultRepository resultRepository;

  @Context
  @Setter
  private ContainerRequestContext containerRequestContext;

  @Override
  public boolean isValid(ResultCreateDTO resultCreateDTO, ConstraintValidatorContext constraintValidatorContext) {
    final CourseAdviser courseAdviser = (CourseAdviser) containerRequestContext.getProperty(CourseAdviserAuthenticationFilter.REQUEST_PROPERTY);

    return !resultRepository.existsByCourseAdviserIdAndCourseCodeAndSessionIdAndSemester(
      courseAdviser.getId(),
      resultCreateDTO.getCourseCode(),
      resultCreateDTO.getSessionId(),
      resultCreateDTO.getSemester()
    );
  }
}
