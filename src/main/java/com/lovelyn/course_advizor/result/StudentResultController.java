package com.lovelyn.course_advizor.result;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.student.Student;
import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
  private ModelMapper modelMapper;

  @POST
  public Response create(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final StudentResultCreateDTO studentResultCreateDTO) {

    final StudentResult studentResult = modelMapper.map(studentResultCreateDTO, StudentResult.class);

    final Student student = new Student();
    student.setId(studentResultCreateDTO.getStudentId());
    studentResult.setStudent(student);

    final Result result = new Result();
    result.setId(studentResultCreateDTO.getResultId());
    studentResult.setResult(result);

    final StudentResult newStudentResult = studentResultRepository.save(studentResult);

    final StudentResultDTO studentResultDTO = modelMapper.map(newStudentResult, StudentResultDTO.class);

    return Response
      .created(null)
      .entity(ResponseDTO.success("Student results created", studentResultDTO))
      .build();
  }

}
