package com.lovelyn.course_advizor.student;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.Utils;
import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserAuthentication;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserAuthenticationFilter;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserRepository;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("student")
@Produces(MediaType.APPLICATION_JSON)
public class StudentController {

  @Context
  @Setter
  private ContainerRequestContext requestContainer;

  @Autowired
  @Setter
  private StudentRepository studentRepository;

  @Autowired
  @Setter
  private CourseAdviserRepository courseAdviserRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  private CourseAdviser getCourseAdviser() {
    return (CourseAdviser) requestContainer.getProperty(CourseAdviserAuthenticationFilter.REQUEST_PROPERTY);
  }

  @POST
  public Response create(
    @NotNull(message = ValidationErrorCode.BODY_INVALID)
    @Valid final StudentCreateDTO studentCreateDTO,
    @Context UriInfo uriInfo
  ) {

    final Student student = modelMapper.map(studentCreateDTO, Student.class);

    courseAdviserRepository.findById(studentCreateDTO.getCourseAdviserId())
      .ifPresent(student::setCourseAdviser);

    student.setPhoneNumber(Utils.phoneNumberToInternationalFormat(student.getPhoneNumber()));

    final Student createdStudent = studentRepository.save(student);

    final StudentDTO studentDTO = modelMapper.map(createdStudent, StudentDTO.class);

    return Response
      .created(uriInfo.getAbsolutePath())
      .entity(new ResponseDTO<>(ResponseDTO.Status.SUCCESS, "Student created", studentDTO))
      .build();
  }

  @GET
  @CourseAdviserAuthentication
  public Response getList() {

    List<Student> students = studentRepository.findAllByCourseAdviserId(getCourseAdviser().getId());

    List<StudentDTO> studentDTOList = students.stream()
      .map(student -> modelMapper.map(student, StudentDTO.class))
      .toList();

    return Response
      .ok(ResponseDTO.success("Students fetched", studentDTOList))
      .build();
  }

}
