package com.lovelyn.course_advizor.result;

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
@ResultFetch
@Priority(Priorities.USER)
public class ResultFetchFilter implements ContainerRequestFilter {

  public static final String REQUEST_PROPERTY = "result";

  @Context
  @Setter
  private ResultRepository resultRepository;

  @Override
  public void filter(final ContainerRequestContext requestContext) {

    try {
      Long id = Long.valueOf(requestContext.getUriInfo().getPathParameters().get("id").get(0));

      final Optional<Result> resultOptional = resultRepository.findById(id);

      resultOptional.ifPresentOrElse(
        result -> requestContext.setProperty(REQUEST_PROPERTY, result),
        ()-> requestContext.abortWith(getErrorResponse())
      );

    } catch (NumberFormatException e) {
      requestContext.abortWith(getErrorResponse());
    }
  }

  private Response getErrorResponse() {
    return Response
      .status(Response.Status.NOT_FOUND)
      .entity(ResponseDTO.error("Result not found", null))
      .type(MediaType.APPLICATION_JSON)
      .build();
  }
}
