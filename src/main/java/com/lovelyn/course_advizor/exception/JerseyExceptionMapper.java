package com.lovelyn.course_advizor.exception;

import com.lovelyn.course_advizor.ResponseDTO;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class JerseyExceptionMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(final Exception exception) {

    Response.ResponseBuilder responseBuilder;

    if (exception instanceof NotFoundException) {
      responseBuilder = Response.status(Response.Status.NOT_FOUND);
    } else if (exception instanceof NotAuthorizedException) {
        responseBuilder = Response.status(Response.Status.UNAUTHORIZED);
    } else {
      responseBuilder = Response.serverError();
    }

    Logger.getLogger(JerseyExceptionMapper.class.getName()).log(Level.SEVERE, null, exception);

    return responseBuilder
      .entity(new ResponseDTO<>(ResponseDTO.Status.ERROR, exception.getMessage(), exception.getStackTrace()))
      .type(MediaType.APPLICATION_JSON)
      .build();
  }

}
