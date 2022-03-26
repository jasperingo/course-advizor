package com.lovelyn.course_advizor.result;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import java.util.Objects;
import java.util.Optional;

public class StudentResultStudentAndResultIdNotChangedValidator implements ConstraintValidator<StudentResultStudentAndResultIdNotChanged, StudentResultCreateDTO> {

  @Context
  @Setter
  private ContainerRequestContext requestContainer;

  @Autowired
  @Setter
  private StudentResultRepository studentResultRepository;

  @Override
  public boolean isValid(StudentResultCreateDTO dto, ConstraintValidatorContext constraintValidatorContext) {

    try {
      Long id = Long.valueOf(requestContainer.getUriInfo().getPathParameters().get("id").get(0));

      final Optional<StudentResult> studentResultOptional = studentResultRepository.findById(id);
      if (studentResultOptional.isPresent()) {
        final StudentResult studentResult = studentResultOptional.get();
        return Objects.equals(studentResult.getStudent().getId(), dto.getStudentId()) &&
          Objects.equals(studentResult.getResult().getId(), dto.getResultId());
      } else
        return false;

    } catch (NumberFormatException e) {
      return false;
    }
  }
}
