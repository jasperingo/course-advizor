package com.lovelyn.course_advizor.section;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SectionIdExistsValidator implements ConstraintValidator<SectionIdExists, Long> {

  @Autowired
  @Setter
  private SectionRepository repository;

  @Override
  public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
    return repository.existsById(id);
  }
}
