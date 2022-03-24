package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.course_adviser.CourseAdviserAuthentication;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("result")
@Produces(MediaType.APPLICATION_JSON)
public class ResultController {

  @POST
  @CourseAdviserAuthentication
  public Response create(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final ResultCreateDTO resultCreateDTO) {

    return Response.ok(resultCreateDTO).build();
  }


}
