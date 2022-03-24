package com.lovelyn.course_advizor.student;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StudentIdExistsValidator implements ConstraintValidator<StudentIdExists, Long> {

  @Autowired
  @Setter
  private StudentRepository studentRepository;

  @Override
  public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
    return studentRepository.existsById(id);
  }
}
