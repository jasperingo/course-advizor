package com.lovelyn.course_advizor.student;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.Utils;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserRepository;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("student")
@Produces(MediaType.APPLICATION_JSON)
public class StudentController {

  @Autowired
  @Setter
  private StudentRepository studentRepository;

  @Autowired
  @Setter
  private CourseAdviserRepository courseAdviserRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;


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

    final Student student1 = studentRepository.save(student);

    final StudentDTO studentDTO = modelMapper.map(student1, StudentDTO.class);

    return Response
      .created(uriInfo.getAbsolutePath())
      .entity(new ResponseDTO<>(ResponseDTO.Status.SUCCESS, "Student created", studentDTO))
      .build();
  }

}
