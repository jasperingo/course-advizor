package com.lovelyn.course_advizor.appointment;

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

@Path("appointment")
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentController {

  @Context
  @Setter
  private ContainerRequestContext requestContainer;

  @Autowired
  @Setter
  private AppointmentRepository appointmentRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;

  private CourseAdviser getCourseAdviser() {
    return (CourseAdviser) requestContainer.getProperty(CourseAdviserAuthenticationFilter.REQUEST_PROPERTY);
  }

  @GET
  @CourseAdviserAuthentication
  public Response getList() {

    List<Appointment> appointments = appointmentRepository.findAllByStudentCourseAdviserId(getCourseAdviser().getId());

    List<AppointmentDTO.AppointmentWithStudentDTO> appointmentWithStudentDTOList = appointments.stream()
      .map(appointment -> modelMapper.map(appointment, AppointmentDTO.AppointmentWithStudentDTO.class))
      .toList();

    return Response
      .ok(ResponseDTO.success("Appointments fetched", appointmentWithStudentDTOList))
      .build();
  }

  @PUT
  @Path("{id}")
  @CourseAdviserAuthentication
  public Response update(
    @PathParam("id") final Long id,
    @NotNull(message = ValidationErrorCode.BODY_INVALID) @Valid final AppointmentUpdateDTO appointmentUpdateDTO
  ) {

    final Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

    Response.ResponseBuilder responseBuilder;

    if (optionalAppointment.isPresent()) {

      Appointment appointment = optionalAppointment.get();

      appointment.setStatus(appointmentUpdateDTO.getStatus());

      appointment.setStartedAt(appointmentUpdateDTO.getStartedAt());

      final Appointment updatedAppointment = appointmentRepository.save(appointment);

      final AppointmentDTO appointmentDTO = modelMapper.map(updatedAppointment, AppointmentDTO.class);

      responseBuilder = Response.ok(ResponseDTO.success("Appointment updated", appointmentDTO));

      // TODO: send call to student...

    } else {
      responseBuilder = Response
        .status(Response.Status.NOT_FOUND)
        .entity(ResponseDTO.error("Appointment not found", null));
    }

    return responseBuilder.build();
  }

}
