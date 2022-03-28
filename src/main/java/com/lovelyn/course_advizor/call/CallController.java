package com.lovelyn.course_advizor.call;

import com.lovelyn.course_advizor.Utils;
import com.lovelyn.course_advizor.appointment.Appointment;
import com.lovelyn.course_advizor.appointment.AppointmentRepository;
import com.lovelyn.course_advizor.result.Result;
import com.lovelyn.course_advizor.result.ResultRepository;
import com.lovelyn.course_advizor.session.Session;
import com.lovelyn.course_advizor.session.SessionRepository;
import com.lovelyn.course_advizor.student.Student;
import com.lovelyn.course_advizor.student.StudentRepository;
import com.lovelyn.course_advizor.student_result.StudentResult;
import com.lovelyn.course_advizor.student_result.StudentResultRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("call")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class CallController {

  @Context
  @Setter
  private UriInfo uriInfo;

  @Context
  @Setter
  private ContainerRequestContext containerRequestContext;

  @Autowired
  @Setter
  private StudentRepository studentRepository;

  @Autowired
  @Setter
  private CallRepository callRepository;

  @Autowired
  @Setter
  private SessionRepository sessionRepository;

  @Autowired
  @Setter
  private ResultRepository resultRepository;

  @Autowired
  @Setter
  private StudentResultRepository studentResultRepository;

  @Autowired
  @Setter
  private AppointmentRepository appointmentRepository;

  private String nextCallbackUrl(final String path) {
    return Utils.replaceUriLastPath(uriInfo.getAbsolutePath(), path);
  }

  private Call getCall() {
    return (Call) containerRequestContext.getProperty(CallFetchByCallSessionIdFilter.REQUEST_PROPERTY);
  }

  @POST
  public CallResponse test() {

    final CallResponse.Say say = new CallResponse.Say();

    say.setValue("Welcome to Course Adviser application");

    final CallResponse callResponse = new CallResponse();

    callResponse.setSay(say);

    return callResponse;
  }

  @POST
  @Path("start")
  @CallActive
  public CallResponse start(@FormParam("callerNumber") final String phoneNumber, @FormParam("sessionId") final String callSessionId) {

    final Optional<Student> optionalStudent = studentRepository.findByPhoneNumber(phoneNumber);

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    if (optionalStudent.isPresent()) {

      final Call call = new Call();

      call.setStudent(optionalStudent.get());

      call.setStatus(Call.Status.ACTIVE);

      call.setCallSessionId(callSessionId);

      callRepository.save(call);

      final CallResponse.GetDigits getDigits = new CallResponse.GetDigits();

      getDigits.setCallbackUrl(nextCallbackUrl("auth"));

      getDigits.setFinishOnKey("#");

      say.setValue("Welcome to Course Adviser application, please enter your matriculation number and press # to continue.");

      getDigits.setSay(say);

      callResponse.setGetDigits(getDigits);

    }  else {

      say.setValue("Your phone number is not registered, thank you for calling.");

      callResponse.setSay(say);
    }

    return callResponse;
  }

  @POST
  @Path("auth")
  @CallActive
  @CallFetchByCallSessionId
  public CallResponse auth(@FormParam("dtmfDigits") final String matriculationNumber) {

    final Call call = getCall();

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    if (call.getStudent().getMatriculationNumber().equals(matriculationNumber)) {

      final CallResponse.GetDigits getDigits = new CallResponse.GetDigits();

      getDigits.setNumDigits(1);

      getDigits.setCallbackUrl(nextCallbackUrl("action"));

      say.setValue(
        String.format("Press %s to check your result, press %s to request an appointment and press %s to send a report.",
          Call.Action.RESULT.word,
          Call.Action.APPOINTMENT.word,
          Call.Action.REPORT.word
        )
      );

      getDigits.setSay(say);

      callResponse.setGetDigits(getDigits);

    } else {

      say.setValue("The matriculation number you entered is invalid, thank you for calling.");

      callResponse.setSay(say);
    }

    return callResponse;
  }

  @POST
  @Path("action")
  @CallActive
  @CallFetchByCallSessionId
  public CallResponse action(@FormParam("dtmfDigits") final int actionNumber) {

    final Call call = getCall();

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    if (actionNumber == Call.Action.RESULT.number) {

      call.setAction(Call.Action.RESULT);

      callRepository.save(call);

      say.setValue("Enter the session start year and end year, separated by an asterisks and press # key to send.");

      final CallResponse.GetDigits getDigits = new CallResponse.GetDigits();

      getDigits.setCallbackUrl(nextCallbackUrl("result-session"));

      getDigits.setFinishOnKey("#");

      getDigits.setSay(say);

      callResponse.setGetDigits(getDigits);

    } else if (actionNumber == Call.Action.APPOINTMENT.number) {

      call.setAction(Call.Action.APPOINTMENT);

      callRepository.save(call);

      final Appointment appointment = new Appointment();

      appointment.setStudent(call.getStudent());

      appointment.setStatus(Appointment.Status.PENDING);

      appointmentRepository.save(appointment);

      say.setValue("Your appointment request is awaiting acceptance by your course adviser, thank you for calling.");

      callResponse.setSay(say);

    } else if (actionNumber == Call.Action.REPORT.number) {

      call.setAction(Call.Action.REPORT);

      callRepository.save(call);

      say.setValue("Please say your report after the beep, and press the # key once you are done.");

      final CallResponse.Record record = new CallResponse.Record();

      record.setFinishOnKey("#");

      record.setCallbackUrl(nextCallbackUrl("report"));

      record.setSay(say);

      callResponse.setRecord(record);
    }

    return callResponse;
  }

  @POST
  @Path("result-session")
  @CallActive
  @CallFetchByCallSessionId
  public CallResponse resultSession(@FormParam("dtmfDigits") final String sessionYears) {

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    try {

       final Integer[] years = Arrays.stream(sessionYears.split("\\*"))
        .map(Integer::parseInt)
        .toArray(Integer[]::new);

       if (years.length < 2) {
         throw new IndexOutOfBoundsException();
       }

      final Optional<Session> optionalSession = sessionRepository.findByStartedAtAndEndedAt(years[0], years[1]);

      final Call call = getCall();

      if (optionalSession.isPresent()) {

        final Session session = optionalSession.get();

        final boolean resultExists = resultRepository.existsByCourseAdviserIdAndSessionId(
          call.getStudent().getCourseAdviser().getId(),
          session.getId()
        );

        if (!resultExists) {

          say.setValue("Sorry, you do not have any result for this session, thank you for calling.");

          callResponse.setSay(say);

        } else {

          call.setSession(session);

          callRepository.save(call);

          say.setValue("Press one for first semester results or press two for second semester results.");

          final CallResponse.GetDigits getDigits = new CallResponse.GetDigits();

          getDigits.setCallbackUrl(nextCallbackUrl("result"));

          getDigits.setNumDigits(1);

          getDigits.setSay(say);

          callResponse.setGetDigits(getDigits);
        }

      } else {
        throw new IllegalArgumentException();
      }

    } catch (IllegalArgumentException | IndexOutOfBoundsException ignored) {

      say.setValue("The session you entered is invalid, thank you for calling.");

      callResponse.setSay(say);
    }

    return callResponse;
  }

  @POST
  @Path("result")
  @CallActive
  @CallFetchByCallSessionId
  public CallResponse result(@FormParam("dtmfDigits") final int semesterNumber) {

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    final Call call = getCall();

    try {

      List<Result> results = null;

      if (semesterNumber == Result.Semester.FIRST.number) {
        results = resultRepository.findAllByCourseAdviserIdAndSessionIdAndSemester(
          call.getStudent().getCourseAdviser().getId(),
          call.getSession().getId(),
          Result.Semester.FIRST
        );
      } else if (semesterNumber == Result.Semester.SECOND.number) {
        results = resultRepository.findAllByCourseAdviserIdAndSessionIdAndSemester(
          call.getStudent().getCourseAdviser().getId(),
          call.getSession().getId(),
          Result.Semester.SECOND
        );
      }

      if (results == null || results.isEmpty()) throw new IllegalArgumentException();

      final String resultString = results.stream()
        .map(result -> {
          final List<StudentResult> studentResults = studentResultRepository.findAllByStudentIdAndResultId(
            call.getStudent().getId(),
            result.getId()
          );
          return studentResults.size() < 1 ? null
            : String.format("in %s you got %s", result.getCourseCode(), studentResults.get(0).getGrade());
        })
        .filter(Objects::nonNull)
        .collect(Collectors.joining(", "));

      if (resultString.isEmpty()) throw new IllegalArgumentException();

      say.setValue(String.format("Please write this down, %s. Thank you for calling.", resultString));

      callResponse.setSay(say);

    } catch (IllegalArgumentException e) {

      say.setValue("Sorry, you do not have any result for this semester of the session, thank you for calling.");

      callResponse.setSay(say);
    }

    return callResponse;
  }

  @POST
  @Path("end")
  @CallFetchByCallSessionId
  public void end(
    @FormParam("durationInSeconds") final Long callDuration,
    @FormParam("amount") final Double callCost,
    @FormParam("recordingUrl") final String recordingUrl
  ) {

    final Call call = getCall();

    call.setCost(callCost);

    call.setDuration(callDuration);

    call.setRecordUrl(recordingUrl);

    call.setStatus(Call.Status.INACTIVE);

    callRepository.save(call);
  }

}
