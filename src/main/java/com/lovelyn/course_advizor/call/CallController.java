package com.lovelyn.course_advizor.call;

import lombok.Setter;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("call")
@Produces(MediaType.APPLICATION_XML)
public class CallController {

  @Context
  @Setter
  private UriInfo uriInfo;

  @POST
  public CallResponse test() {

    final CallResponse.Say say = new CallResponse.Say();

    say.setValue("Welcome to Course Adviser application");

    final CallResponse callResponse = new CallResponse();

    callResponse.setSay(say);

    return callResponse;
  }

  @POST
  @Path("start")
  public CallResponse start() {

    final CallResponse.GetDigits getDigits = new CallResponse.GetDigits();

    getDigits.setCallbackUrl(uriInfo.getAbsolutePath().toString());

    getDigits.setFinishOnKey("#");

    final CallResponse callResponse = new CallResponse();

    callResponse.setGetDigits(getDigits);

    return callResponse;
  }

}
