package com.lovelyn.course_advizor.course_adviser;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.Utils;
import com.lovelyn.course_advizor.department.DepartmentRepository;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import com.lovelyn.course_advizor.session.SessionRepository;
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
import javax.ws.rs.core.UriInfo;
import java.util.Objects;
import java.util.Optional;

@Path("course-adviser")
@Produces(MediaType.APPLICATION_JSON)
public class CourseAdviserController {

  @Autowired
  @Setter
  private CourseAdviserRepository repository;

  @Autowired
  @Setter
  private DepartmentRepository departmentRepository;

  @Autowired
  @Setter
  private SessionRepository sessionRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  @POST
  public Response create(
    @Context final UriInfo uriInfo,
    @NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final CourseAdviserCreateDTO courseAdviserDTO
  ) {

    final CourseAdviser courseAdviser = modelMapper.map(courseAdviserDTO, CourseAdviser.class);

    sessionRepository.findById(courseAdviserDTO.getSessionId()).ifPresent(courseAdviser::setSession);
    departmentRepository.findById(courseAdviserDTO.getDepartmentId()).ifPresent(courseAdviser::setDepartment);

    courseAdviser.setPhoneNumber(Utils.phoneNumberToInternationalFormat(courseAdviser.getPhoneNumber()));

    final CourseAdviser newCourseAdviser = repository.save(courseAdviser);

    final CourseAdviserDTO responseEntity = modelMapper.map(newCourseAdviser, CourseAdviserDTO.class);

    return Response
      .created(uriInfo.getAbsolutePath())
      .entity(new ResponseDTO<>(ResponseDTO.Status.SUCCESS, "Course adviser created", responseEntity))
      .build();
  }

  @POST
  @Path("auth")
  public Response auth(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final CourseAdviserAuthDTO courseAdviserDTO) {

    final Optional<CourseAdviser> courseAdviserOptional = repository.findByPhoneNumber(courseAdviserDTO.getPhoneNumber());

    final CourseAdviser courseAdviser = courseAdviserOptional.orElseThrow(()-> new NotAuthorizedException("Credentials are incorrect"));

    if (Objects.equals(courseAdviserDTO.getPin(), courseAdviser.getPin())) {

      final CourseAdviserDTO responseEntity = modelMapper.map(courseAdviser, CourseAdviserDTO.class);

      return Response
        .ok(new ResponseDTO<>(ResponseDTO.Status.SUCCESS, "Authenticated", responseEntity))
        .build();

    } else {
      throw new NotAuthorizedException("Credentials are incorrect");
    }
  }

  @GET
  @CourseAdviserAuthentication
  public Response get(@Context final ContainerRequestContext requestContainer) {

    final CourseAdviser courseAdviser = (CourseAdviser) requestContainer.getProperty("user");

    final CourseAdviserDTO courseAdviserDTO = modelMapper.map(courseAdviser, CourseAdviserDTO.class);

    return Response
      .ok(new ResponseDTO<>(ResponseDTO.Status.SUCCESS, "Course Adviser fetched", courseAdviserDTO))
      .build();
  }

}
