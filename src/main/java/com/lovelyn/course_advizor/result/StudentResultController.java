package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.student.StudentRepository;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

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

  @POST
  public Response create(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final StudentResultCreateDTO studentResultCreateDTO) {

    if (studentResultRepository.existsByStudentIdAndResultId(studentResultCreateDTO.getStudentId(), studentResultCreateDTO.getResultId())) {
      throw new BadRequestException("Student result with student and result id already exists");
    }

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
  public Response update(
    @PathParam("id") final Long id,
    @NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final StudentResultCreateDTO studentResultCreateDTO
  ) {

    final StudentResult studentResult = studentResultRepository.findById(id).orElseThrow(() -> new NotFoundException("Student result not found"));

    if (
      !Objects.equals(studentResult.getStudent().getId(), studentResultCreateDTO.getStudentId()) ||
        !Objects.equals(studentResult.getResult().getId(), studentResultCreateDTO.getResultId())
    ) {
      throw new BadRequestException("Student result student id and result id cannot be change");
    }

    studentResult.setGrade(studentResultCreateDTO.getGrade());

    final StudentResult updatedStudentResult = studentResultRepository.save(studentResult);

    final StudentResultDTO studentResultDTO = modelMapper.map(updatedStudentResult, StudentResultDTO.class);

    return Response
      .ok(ResponseDTO.success("Student result updated", studentResultDTO))
      .build();
  }

}
