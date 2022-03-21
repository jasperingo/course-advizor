package com.lovelyn.course_advizor.department;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentIdExistsValidator implements ConstraintValidator<DepartmentIdExists, Long> {

  @Autowired
  @Setter
  private DepartmentRepository repository;

  @Override
  public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
    return repository.existsById(id);
  }

}
