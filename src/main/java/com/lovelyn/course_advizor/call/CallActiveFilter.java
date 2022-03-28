package com.lovelyn.course_advizor.call;

import com.lovelyn.course_advizor.Utils;
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

@Provider
@CallActive
@Priority(Priorities.USER)
public class CallActiveFilter implements ContainerRequestFilter {

  @Context
  @Setter
  private UriInfo uriInfo;

  @Override
  public void filter(final ContainerRequestContext requestContext) {

    final ContainerRequest containerRequest = (ContainerRequest) requestContext;

    try {

      containerRequest.bufferEntity();

      final Form form = containerRequest.readEntity(Form.class);

      String isActive = form.asMap().get("isActive").get(0);

      if (isActive.equals("0"))
        throw new IllegalArgumentException();

    } catch (ProcessingException | NullPointerException | IndexOutOfBoundsException | IllegalArgumentException e) {
      requestContext.abortWith(redirect());
    }
  }

  private Response redirect() {

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Redirect redirect = new CallResponse.Redirect();

    redirect.setValue(Utils.replaceUriLastPath(uriInfo.getAbsolutePath(), "end"));

    callResponse.setRedirect(redirect);

    return Response.ok(callResponse).build();
  }

}
