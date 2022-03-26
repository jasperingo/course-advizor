package com.lovelyn.course_advizor.student_result;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.result.ResultRepository;
import com.lovelyn.course_advizor.student.StudentRepository;
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

@Path("student-result")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResultController {

  @Autowired
  @Setter
  private StudentResultRepository studentResultRepository;

  @Autowired
  @Setter
  private StudentRepository studentRepository;

  @Autowired
  @Setter
  private ResultRepository resultRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  @Context
  @Setter
  private ContainerRequestContext containerRequestContext;

  @POST
  public Response create(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final StudentResultCreateDTO studentResultCreateDTO) {

    final StudentResult studentResult = modelMapper.map(studentResultCreateDTO, StudentResult.class);

    studentRepository.findById(studentResultCreateDTO.getStudentId()).ifPresent(studentResult::setStudent);

    resultRepository.findById(studentResultCreateDTO.getResultId()).ifPresent(studentResult::setResult);

    final StudentResult newStudentResult = studentResultRepository.save(studentResult);

    final StudentResultDTO studentResultDTO = modelMapper.map(newStudentResult, StudentResultDTO.class);

    return Response
      .created(null)
      .entity(ResponseDTO.success("Student results created", studentResultDTO))
      .build();
  }

  @PUT
  @Path("{id}")
  @StudentResultFetch
  public Response update(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final StudentResultUpdateDTO studentResultCreateDTO) {

    final StudentResult studentResult = (StudentResult) containerRequestContext.getProperty(StudentResultFetchFilter.REQUEST_PROPERTY);

    studentResult.setGrade(studentResultCreateDTO.getGrade());

    final StudentResult updatedStudentResult = studentResultRepository.save(studentResult);

    final StudentResultDTO studentResultDTO = modelMapper.map(updatedStudentResult, StudentResultDTO.class);

    return Response
      .ok(ResponseDTO.success("Student result updated", studentResultDTO))
      .build();
  }

}
