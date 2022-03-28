package com.lovelyn.course_advizor.report;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.course_adviser.CourseAdviser;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserAuthentication;
import com.lovelyn.course_advizor.course_adviser.CourseAdviserAuthenticationFilter;
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
import java.util.Optional;

@Path("report")
@Produces(MediaType.APPLICATION_JSON)
public class ReportController {

  @Context
  @Setter
  private ContainerRequestContext requestContainer;

  @Autowired
  @Setter
  private ReportRepository reportRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  private CourseAdviser getCourseAdviser() {
    return (CourseAdviser) requestContainer.getProperty(CourseAdviserAuthenticationFilter.REQUEST_PROPERTY);
  }

  @GET
  @CourseAdviserAuthentication
  public Response getList() {

    List<Report> reports = reportRepository.findAllByStudentCourseAdviserId(getCourseAdviser().getId());

    List<ReportDTO.ReportWithStudentDTO> reportWithStudentDTOList = reports.stream()
      .map(report -> modelMapper.map(report, ReportDTO.ReportWithStudentDTO.class))
      .toList();

    return Response
      .ok(ResponseDTO.success("Reports fetched", reportWithStudentDTOList))
      .build();
  }

  @PUT
  @Path("{id}")
  public Response update(
    @PathParam("id") final Long id,
    @NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final ReportUpdateDTO reportUpdateDTO
  ) {

    final Optional<Report> optionalReport = reportRepository.findById(id);

    Response.ResponseBuilder responseBuilder;

    if (optionalReport.isPresent()) {

      final Report report = optionalReport.get();

      report.setReply(reportUpdateDTO.getReply());

      final Report updatedReport = reportRepository.save(report);

      final ReportDTO reportDTO = modelMapper.map(updatedReport, ReportDTO.class);

      responseBuilder = Response.ok(ResponseDTO.success("Report updated", reportDTO));

      // TODO: send call to student...

    } else {
      responseBuilder = Response
        .status(Response.Status.NOT_FOUND)
        .entity(ResponseDTO.error("Report not found", null));
    }

    return responseBuilder.build();
  }

}
