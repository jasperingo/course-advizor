package com.lovelyn.course_advizor.course_adviser;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseAdviserPhoneNumberAlreadyExistsValidator implements ConstraintValidator<CourseAdviserPhoneNumberAlreadyExists, String> {

  @Autowired
  @Setter
  private CourseAdviserRepository repository;

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return !repository.existsByPhoneNumber(s);
  }

}
