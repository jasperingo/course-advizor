package com.lovelyn.course_advizor.course_adviser;

import com.lovelyn.course_advizor.ResponseDTO;
import com.lovelyn.course_advizor.Utils;
import com.lovelyn.course_advizor.appointment.AppointmentRepository;
import com.lovelyn.course_advizor.department.DepartmentRepository;
import com.lovelyn.course_advizor.report.ReportRepository;
import com.lovelyn.course_advizor.result.ResultRepository;
import com.lovelyn.course_advizor.session.SessionRepository;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("course-adviser")
@Produces(MediaType.APPLICATION_JSON)
public class CourseAdviserController {

  @Context
  @Setter
  private ContainerRequestContext containerRequestContext;

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
  private StudentRepository studentRepository;

  @Autowired
  @Setter
  private ResultRepository resultRepository;

  @Autowired
  @Setter
  private AppointmentRepository appointmentRepository;

  @Autowired
  @Setter
  private ReportRepository reportRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  @POST
  public Response create(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final CourseAdviserCreateDTO courseAdviserDTO) {

    final CourseAdviser courseAdviser = modelMapper.map(courseAdviserDTO, CourseAdviser.class);

    sessionRepository.findById(courseAdviserDTO.getSessionId()).ifPresent(courseAdviser::setSession);
    departmentRepository.findById(courseAdviserDTO.getDepartmentId()).ifPresent(courseAdviser::setDepartment);

    courseAdviser.setPhoneNumber(Utils.phoneNumberToInternationalFormat(courseAdviser.getPhoneNumber()));

    final CourseAdviser newCourseAdviser = repository.save(courseAdviser);

    final CourseAdviserDTO responseEntity = modelMapper.map(newCourseAdviser, CourseAdviserDTO.class);

    return Response
      .created(null)
      .entity(ResponseDTO.success("Course adviser created", responseEntity))
      .build();
  }

  @POST
  @Path("auth")
  public Response auth(@NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final CourseAdviserAuthDTO courseAdviserDTO) {

    final Optional<CourseAdviser> courseAdviserOptional = repository.findByPhoneNumber(Utils.phoneNumberToInternationalFormat(courseAdviserDTO.getPhoneNumber()));

    final CourseAdviser courseAdviser = courseAdviserOptional.orElseThrow(()-> new NotAuthorizedException("Credentials are incorrect"));

    if (Objects.equals(courseAdviserDTO.getPin(), courseAdviser.getPin())) {

      final CourseAdviserDTO responseEntity = modelMapper.map(courseAdviser, CourseAdviserDTO.class);

      return Response
        .ok(ResponseDTO.success("Authenticated", responseEntity))
        .build();

    } else {
      throw new NotAuthorizedException("Credentials are incorrect");
    }
  }

  @GET
  @Path("{id}")
  public Response get(@PathParam("id") final Long id) {

    final Optional<CourseAdviser> optionalCourseAdviser = repository.findById(id);

    if (optionalCourseAdviser.isPresent()) {

      final CourseAdviserDTO courseAdviserDTO = modelMapper.map(optionalCourseAdviser.get(), CourseAdviserDTO.class);

      return Response
        .ok(ResponseDTO.success("Course Adviser fetched", courseAdviserDTO))
        .build();

    } else {

      return Response
        .status(Response.Status.NOT_FOUND)
        .entity(ResponseDTO.error("Course adviser not found", null))
        .build();
    }
  }

  @GET
  public Response getList() {

    final List<CourseAdviser> courseAdvisers = repository.findAll();

    final List<CourseAdviserDTO> courseAdviserDTOList = courseAdvisers.stream()
      .map(courseAdviser -> modelMapper.map(courseAdviser, CourseAdviserDTO.class))
      .toList();

    return Response
      .ok(ResponseDTO.success("Course Advisers fetched", courseAdviserDTOList))
      .build();
  }

  @GET
  @Path("statistics")
  @CourseAdviserAuthentication
  public Response getStatistics() {

    final CourseAdviser courseAdviser = (CourseAdviser) containerRequestContext.getProperty(CourseAdviserAuthenticationFilter.REQUEST_PROPERTY);

    final CourseAdviserStatisticsDTO statisticsDTO = new CourseAdviserStatisticsDTO();

    statisticsDTO.setNumberOfStudents(studentRepository.countByCourseAdviserId(courseAdviser.getId()));

    statisticsDTO.setNumberOfResults(resultRepository.countByCourseAdviserId(courseAdviser.getId()));

    statisticsDTO.setNumberOfAppointments(appointmentRepository.countByStudentCourseAdviserId(courseAdviser.getId()));

    statisticsDTO.setNumberOfPendingAppointments(appointmentRepository.countPendingByStudentCourseAdviserId(courseAdviser.getId()));

    statisticsDTO.setNumberOfReports(reportRepository.countByStudentCourseAdviserId(courseAdviser.getId()));

    statisticsDTO.setNumberOfPendingReports(reportRepository.countPendingByStudentCourseAdviserId(courseAdviser.getId()));

    return Response
      .ok(ResponseDTO.success("Statistics fetched", statisticsDTO))
      .build();
  }

}
