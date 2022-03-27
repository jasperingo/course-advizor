package com.lovelyn.course_advizor.student;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StudentPhoneNumberAlreadyExistsValidator implements ConstraintValidator<StudentPhoneNumberAlreadyExists, String> {

  @Autowired
  @Setter
  private StudentRepository studentRepository;

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return !studentRepository.existsByPhoneNumber(s);
  }

}
