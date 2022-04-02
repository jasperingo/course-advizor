package com.lovelyn.course_advizor.course_adviser;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseAdviserWithSessionIdAndDepartmentIdAlreadyExistsValidator
  implements ConstraintValidator<CourseAdviserWithSessionIdAndDepartmentIdAlreadyExists, CourseAdviserCreateDTO> {

  @Autowired
  @Setter
  private CourseAdviserRepository courseAdviserRepository;

  @Override
  public boolean isValid(CourseAdviserCreateDTO courseAdviserCreateDTO, ConstraintValidatorContext constraintValidatorContext) {
    return !courseAdviserRepository.existsBySessionIdAndDepartmentId(
      courseAdviserCreateDTO.getSessionId(),
      courseAdviserCreateDTO.getDepartmentId()
    );
  }
}
