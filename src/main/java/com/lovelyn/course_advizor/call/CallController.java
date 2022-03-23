package com.lovelyn.course_advizor.call;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("call")
@Produces(MediaType.APPLICATION_XML)
public class CallController {

  @POST
  public CallResponse test() {

    final CallResponse.Say say = new CallResponse.Say();

    say.setValue("Welcome to Course Adviser application");

    final CallResponse callResponse = new CallResponse();

    callResponse.setSay(say);

    return callResponse;
  }
}
