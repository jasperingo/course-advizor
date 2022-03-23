package com.lovelyn.course_advizor.session;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SessionIdExistsValidator implements ConstraintValidator<SessionIdExists, Long> {

  @Autowired
  @Setter
  private SessionRepository repository;

  @Override
  public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
    return repository.existsById(id);
  }
}
