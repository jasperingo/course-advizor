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
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
@CallActive
@Priority(Priorities.AUTHORIZATION)
public class CallActiveFilter implements ContainerRequestFilter {

  @Context
  @Setter
  private UriInfo uriInfo;

  @Context
  @Setter
  private CallRepository callRepository;

  @Override
  public void filter(final ContainerRequestContext requestContext) {

    final ContainerRequest containerRequest = (ContainerRequest) requestContext;

    try {

      containerRequest.bufferEntity();

      final Form form = containerRequest.readEntity(Form.class);

      final String isActive = form.asMap().get("isActive").get(0);

      if (isActive.equals("0")) {
        final String callSessionId = form.asMap().get("sessionId").get(0);
        final Long callDuration = Long.valueOf(form.asMap().get("durationInSeconds").get(0));
        final Double callCost = Double.valueOf(form.asMap().get("amount").get(0));

        final Optional<Call> optionalCall = callRepository.findByCallSessionId(callSessionId);

        if (optionalCall.isPresent()) {

          final Call call = optionalCall.get();

          call.setCost(callCost);

          call.setDuration(callDuration);

          call.setStatus(Call.Status.INACTIVE);

          callRepository.save(call);
        }

        requestContext.abortWith(Response.ok().build());
      }

    } catch (ProcessingException | NullPointerException | IndexOutOfBoundsException e) {
      requestContext.abortWith(Response.serverError().build());
    }
  }

}
