package com.lovelyn.course_advizor.course_adviser;

import com.lovelyn.course_advizor.ResponseDTO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
@CourseAdviserAuthentication
@Priority(Priorities.AUTHENTICATION)
public class CourseAdviserAuthenticationFilter implements ContainerRequestFilter {

  public static final String REQUEST_PROPERTY = "user";

  @Autowired
  @Setter
  private CourseAdviserRepository repository;

  @Override
  public void filter(final ContainerRequestContext requestContext) {

    Optional<String> authHeader = Optional.ofNullable(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION));

    authHeader.ifPresentOrElse(
      token -> {
        try {
          Optional<CourseAdviser> courseAdviserOptional = repository.findById(Long.valueOf(token));
          courseAdviserOptional.ifPresentOrElse(
            courseAdviser -> requestContext.setProperty(REQUEST_PROPERTY, courseAdviser),
            () -> requestContext.abortWith(getNotAuthResponse())
          );
        } catch (NumberFormatException ignored) {
          requestContext.abortWith(getNotAuthResponse());
        }
      },
      () -> requestContext.abortWith(getNotAuthResponse())
    );
  }

  private Response getNotAuthResponse() {
    return Response
      .status(Response.Status.UNAUTHORIZED)
      .entity(new ResponseDTO<>(ResponseDTO.Status.ERROR, "Authentication failed", null))
      .type(MediaType.APPLICATION_JSON)
      .build();
  }

}
