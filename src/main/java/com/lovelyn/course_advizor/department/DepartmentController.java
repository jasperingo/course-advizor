package com.lovelyn.course_advizor.department;

import com.lovelyn.course_advizor.ResponseDTO;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("department")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentController {

  @Autowired
  @Setter
  private DepartmentRepository departmentRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  @GET
  public Response getList() {

    final List<Department> departments = departmentRepository.findAll();

    final List<DepartmentDTO> departmentDTOS = departments.stream()
      .map(department -> modelMapper.map(department, DepartmentDTO.class))
      .toList();

    return Response
      .ok(ResponseDTO.success("Departments fetched", departmentDTOS))
      .build();
  }

}
