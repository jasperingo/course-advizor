package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserAuthentication;
import com.lovelyn.course_advizor.session.SessionRepository;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("result")
@CourseAdviserAuthentication
@Produces(MediaType.APPLICATION_JSON)
public class ResultController {

  @Autowired
  @Setter
  private ResultRepository resultRepository;

  @Autowired
  @Setter
  private SessionRepository sessionRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  @Context
  @Setter
  private ContainerRequestContext requestContainer;

  @POST
  public Response create(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final ResultCreateDTO resultCreateDTO) {

    final Result result = modelMapper.map(resultCreateDTO, Result.class);

    result.setCourseAdviser((CourseAdviser) requestContainer.getProperty("user"));
    sessionRepository.findById(resultCreateDTO.getSessionId()).ifPresent(result::setSession);

    final Result newResult = resultRepository.save(result);

    final ResultDTO resultDTO = modelMapper.map(newResult, ResultDTO.class);

    return Response
      .created(null)
      .entity(ResponseDTO.success("Result created", resultDTO))
      .build();
  }

  @GET
  @Path("{id}")
  public Response get(@PathParam("id") final Long id) {

    final Result result = resultRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Result not found"));

    final ResultDTO resultDTO = modelMapper.map(result, ResultDTO.class);

    return Response
      .ok(ResponseDTO.success("Result fetched", resultDTO))
      .build();
  }

  @GET
  @Path("{id}/student")
  public Response getStudents() {

    return Response
      .ok(ResponseDTO.success("Result students fetched", null))
      .build();
  }


}
