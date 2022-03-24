package com.lovelyn.course_advizor.exception;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.validation.ValidationError;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Provider
public class JerseyConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(final ConstraintViolationException exception) {

    final List<ValidationError> errors = exception.getConstraintViolations().stream()
      .map(constraintViolation -> {

          final String pathProperty = constraintViolation.getPropertyPath().toString();
          final Matcher indexMatcher = Pattern.compile("\\[(\\d)]").matcher(pathProperty);

          final String[] errorMessage = constraintViolation.getMessage().split(" / ");

          return new ValidationError(
            errorMessage.length > 0 ? errorMessage[0] : null,
            errorMessage.length > 2 ? errorMessage[2] : null,
            errorMessage.length > 1 ? errorMessage[1] : null,
            constraintViolation.getInvalidValue(),
            indexMatcher.find() ? Integer.valueOf(indexMatcher.group(1)) : null
          );
        }
      )
      .toList();

    return Response
      .status(Response.Status.BAD_REQUEST)
      .entity(new ResponseDTO<>(ResponseDTO.Status.ERROR, null, errors))
      .type(MediaType.APPLICATION_JSON).build();
  }

}