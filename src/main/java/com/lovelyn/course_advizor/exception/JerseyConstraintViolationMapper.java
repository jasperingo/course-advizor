package com.lovelyn.course_advizor.exception;

import com.lovelyn.course_advizor.ResponseDTO;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class JerseyConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {

        final List<ValidationError> errors = exception.getConstraintViolations().stream()
            .map(constraintViolation -> {

                final String[] errorMessage = constraintViolation.getMessage().split(" / ");

                return new ValidationError(
                  errorMessage.length > 0 ? errorMessage[0] : null,
                  errorMessage.length > 2 ? errorMessage[2] : null,
                  errorMessage.length > 1 ? errorMessage[1] : null,
                  constraintViolation.getInvalidValue()
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