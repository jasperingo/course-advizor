package com.lovelyn.course_advizor.result;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResultIdExistsValidator implements ConstraintValidator<ResultIdExists, Long> {

  @Autowired
  @Setter
  private ResultRepository resultRepository;

  @Override
  public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
    return resultRepository.existsById(aLong);
  }
}
