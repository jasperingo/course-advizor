package com.lovelyn.course_advizor.course_adviser;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseAdviserIdExistsValidator implements ConstraintValidator<CourseAdviserIdExists, Long> {

  @Autowired
  @Setter
  private CourseAdviserRepository courseAdviserRepository;

  @Override
  public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
    return courseAdviserRepository.existsById(aLong);
  }

}
