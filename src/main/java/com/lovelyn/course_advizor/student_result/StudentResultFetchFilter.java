package com.lovelyn.course_advizor.student_result;

import com.lovelyn.course_advizor.ResponseDTO;
import lombok.Setter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
@StudentResultFetch
@Priority(Priorities.USER)
public class StudentResultFetchFilter implements ContainerRequestFilter {

  public static final String REQUEST_PROPERTY = "studentResult";

  @Context
  @Setter
  private StudentResultRepository studentResultRepository;

  @Override
  public void filter(final ContainerRequestContext requestContext) {

    try {
      Long id = Long.valueOf(requestContext.getUriInfo().getPathParameters().get("id").get(0));

      final Optional<StudentResult> studentResultOptional = studentResultRepository.findById(id);

      studentResultOptional.ifPresentOrElse(
        studentResult -> requestContext.setProperty(REQUEST_PROPERTY, studentResult),
        ()-> requestContext.abortWith(getErrorResponse())
      );

    } catch (NumberFormatException e) {
      requestContext.abortWith(getErrorResponse());
    }
  }

  private Response getErrorResponse() {
    return Response
      .status(Response.Status.NOT_FOUND)
      .entity(ResponseDTO.error("Student result not found", null))
      .type(MediaType.APPLICATION_JSON)
      .build();
  }

}
