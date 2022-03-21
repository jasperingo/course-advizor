package com.lovelyn.course_advizor.course_adviser;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.department.Department;
import com.lovelyn.course_advizor.department.DepartmentRepository;
import com.lovelyn.course_advizor.exception.ValidationErrorCode;
import com.lovelyn.course_advizor.section.Section;
import com.lovelyn.course_advizor.section.SectionRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
  private SectionRepository sectionRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  @POST
  @Path("create")
  public Response create(
    @NotNull(
      message = "request_body / "+
        ValidationErrorCode.BODY_INVALID +
        " / Request body cannot be null"
    )
    @Valid final CourseAdviserCreateDTO courseAdviserDTO,
    @Context final UriInfo uriInfo
  ) {

    final CourseAdviser courseAdviser = modelMapper.map(courseAdviserDTO, CourseAdviser.class);

    final Optional<Section> section = sectionRepository.findById(courseAdviserDTO.getSectionId());
    final Optional<Department> department = departmentRepository.findById(courseAdviserDTO.getDepartmentId());

    if (section.isPresent() && department.isPresent()) {
      courseAdviser.setSection(section.get());
      courseAdviser.setDepartment(department.get());
    }

    final CourseAdviser newCourseAdviser = repository.save(courseAdviser);

    final CourseAdviserDTO responseEntity = modelMapper.map(newCourseAdviser, CourseAdviserDTO.class);

    return Response
      .created(uriInfo.getAbsolutePath())
      .entity(new ResponseDTO<>(ResponseDTO.Status.SUCCESS, "Course adviser created", responseEntity))
      .build();
  }

  @GET
  @Path("{id}")
  public Response get(@PathParam("id") final Long id) {

    final Optional<CourseAdviser> courseAdviser = repository.findById(id);

    if (courseAdviser.isPresent()) {
      final CourseAdviserDTO courseAdviserDTO = modelMapper.map(courseAdviser.get(), CourseAdviserDTO.class);

      return Response
        .ok(new ResponseDTO<>(ResponseDTO.Status.SUCCESS, "Course Adviser fetched", courseAdviserDTO))
        .build();
    } else {
      throw new NotFoundException("Course adviser not found");
    }
  }

  
}
