package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserAuthentication;
import com.lovelyn.course_advizor.session.SessionRepository;
import com.lovelyn.course_advizor.student.Student;
import com.lovelyn.course_advizor.student.StudentDTO;
import com.lovelyn.course_advizor.student.StudentRepository;
import com.lovelyn.course_advizor.student_result.StudentResultRepository;
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
import java.util.List;

@Path("result")
@CourseAdviserAuthentication
@Produces(MediaType.APPLICATION_JSON)
public class ResultController {

  @Autowired
  @Setter
  private ResultRepository resultRepository;

  @Autowired
  @Setter
  private StudentResultRepository studentResultRepository;

  @Autowired
  @Setter
  private SessionRepository sessionRepository;

  @Autowired
  @Setter
  private StudentRepository studentRepository;

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
  @ResultFetch
  @Path("{id}")
  public Response get() {

    final Result result = (Result) requestContainer.getProperty(ResultFetchFilter.REQUEST_PROPERTY);

    final ResultDTO resultDTO = modelMapper.map(result, ResultDTO.class);

    return Response
      .ok(ResponseDTO.success("Result fetched", resultDTO))
      .build();
  }

  @GET
  @ResultFetch
  @Path("{id}/student")
  public Response getStudents(@PathParam("id") final Long id) {

    final CourseAdviser courseAdviser = (CourseAdviser) requestContainer.getProperty("user");

    List<Student> students = studentRepository.findAllByCourseAdviserId(courseAdviser.getId());

    List<StudentDTO.StudentWithResultDTO> studentDTOList = students.stream()
      .map(student -> {
        student.setStudentResult(studentResultRepository.findAllByStudentIdAndResultId(student.getId(), id));
        return modelMapper.map(student, StudentDTO.StudentWithResultDTO.class);
      })
      .toList();

    return Response
      .ok(ResponseDTO.success("Result students fetched", studentDTOList))
      .build();
  }

}
