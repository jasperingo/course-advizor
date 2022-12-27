package com.lovelyn.course_advizor.call;

import lombok.Setter;
import org.glassfish.jersey.server.ContainerRequest;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
@Priority(Priorities.AUTHENTICATION)
@CallFetchByCallSessionId
public class CallFetchByCallSessionIdFilter implements ContainerRequestFilter {

  public static final String REQUEST_PROPERTY = "call";

  @Context
  @Setter
  private CallRepository callRepository;

  @Override
  public void filter(final ContainerRequestContext requestContext) {

    final ContainerRequest containerRequest = (ContainerRequest) requestContext;

    try {
      containerRequest.bufferEntity();

      final Form form = containerRequest.readEntity(Form.class);

      final String callSessionId = form.asMap().get("sessionId").get(0);


      final Optional<Call> optionalCall = callRepository.findByCallSessionId(callSessionId);

      optionalCall.ifPresentOrElse(
        call -> requestContext.setProperty(REQUEST_PROPERTY, call),
        () -> {
          if (Call.CallDirection.valueOf(form.asMap().get("direction").get(0)) == Call.CallDirection.Outbound) {
            requestContext.setProperty(REQUEST_PROPERTY, null);
          } else {
            requestContext.abortWith(response());
          }
        }
      );

    } catch (ProcessingException | NullPointerException | IndexOutOfBoundsException e) {
      requestContext.abortWith(response());
    }
  }

  private Response response() {
    return Response.status(Response.Status.NO_CONTENT).build();
  }

}
