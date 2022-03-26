package com.lovelyn.course_advizor.student_result;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StudentResultStudentAndResultIdAlreadyExistsValidator implements ConstraintValidator<StudentResultStudentAndResultIdAlreadyExists, StudentResultCreateDTO> {

  @Autowired
  @Setter
  private StudentResultRepository studentResultRepository;

  @Override
  public boolean isValid(StudentResultCreateDTO studentResultCreateDTO, ConstraintValidatorContext constraintValidatorContext) {

    return !studentResultRepository.existsByStudentIdAndResultId(
      studentResultCreateDTO.getStudentId(),
      studentResultCreateDTO.getResultId()
    );
  }

}
