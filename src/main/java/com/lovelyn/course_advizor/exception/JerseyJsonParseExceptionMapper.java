package com.lovelyn.course_advizor.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.lovelyn.course_advizor.ResponseDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class JerseyJsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

  @Override
  public Response toResponse(JsonParseException exception) {

    Logger.getLogger(JerseyJsonParseExceptionMapper.class.getName()).log(Level.SEVERE, null, exception);

    return Response.status(Response.Status.BAD_REQUEST)
      .entity(new ResponseDTO<>(ResponseDTO.Status.ERROR, exception.getMessage(), exception.getStackTrace()))
      .type(MediaType.APPLICATION_JSON)
      .build();
  }
}
