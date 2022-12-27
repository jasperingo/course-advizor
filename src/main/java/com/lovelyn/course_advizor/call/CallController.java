package com.lovelyn.course_advizor.call;

import com.lovelyn.course_advizor.Utils;
import com.lovelyn.course_advizor.appointment.Appointment;
import com.lovelyn.course_advizor.appointment.AppointmentRepository;
import com.lovelyn.course_advizor.report.Report;
import com.lovelyn.course_advizor.report.ReportRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

@Component
@Path("call")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class CallController {
  @Value("${af.redirectInvalidOutboundCall}")
  private String redirectInvalidOutboundCall;

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

  @Autowired
  @Setter
  private ReportRepository reportRepository;

  private String nextCallbackUrl(final String path) {
    return Utils.replaceUriLastPath(uriInfo.getAbsolutePath(), path);
  }

  private Call getCall() {
    return (Call) containerRequestContext.getProperty(CallFetchByCallSessionIdFilter.REQUEST_PROPERTY);
  }

  @POST
  @Path("test")
  public CallResponse test() {

    final CallResponse.Say say = new CallResponse.Say();

    say.setValue("Welcome to Course Adviser application");

    final CallResponse callResponse = new CallResponse();

    callResponse.setSay(say);

    return callResponse;
  }

  @POST
  @CallActive
  @Path("start")
  public CallResponse start(@FormParam("direction") final Call.CallDirection callDirection) {
    final CallResponse callResponse = new CallResponse();

    final CallResponse.Redirect redirect = new CallResponse.Redirect();

    switch (callDirection) {
      case Inbound -> redirect.setValue(nextCallbackUrl( "inbound"));

      case Outbound -> redirect.setValue(nextCallbackUrl("outbound"));
    }

    callResponse.setRedirect(redirect);

    return callResponse;
  }

  @POST
  @Path("inbound")
  @CallActive
  public CallResponse inbound(
    @FormParam("callerNumber") final String phoneNumber,
    @FormParam("sessionId") final String callSessionId
  ) {

    final Optional<Student> optionalStudent = studentRepository.findByPhoneNumber(phoneNumber);

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    if (optionalStudent.isPresent()) {

      final Call call = new Call();

      call.setStudent(optionalStudent.get());

      call.setStatus(Call.Status.ACTIVE);

      call.setCallSessionId(callSessionId);

      call.setCallDirection(Call.CallDirection.Inbound);

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
  public CallResponse action(@FormParam("dtmfDigits") final Call.Action action) {

    final Call call = getCall();

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    if (action == null) {

      say.setValue("You pressed an invalid key, thank you for calling.");

      callResponse.setSay(say);

    } else {

      switch (action) {

        case RESULT -> {

          call.setAction(Call.Action.RESULT);

          callRepository.save(call);

          say.setValue("Enter the session start year and end year, separated by an asterisks and press # key to send.");

          final CallResponse.GetDigits getDigits = new CallResponse.GetDigits();

          getDigits.setCallbackUrl(nextCallbackUrl("result-session"));

          getDigits.setFinishOnKey("#");

          getDigits.setSay(say);

          callResponse.setGetDigits(getDigits);
        }

        case APPOINTMENT -> {

          call.setAction(Call.Action.APPOINTMENT);

          callRepository.save(call);

          final Appointment appointment = new Appointment();

          appointment.setStudent(call.getStudent());

          appointment.setStatus(Appointment.Status.PENDING);

          appointmentRepository.save(appointment);

          say.setValue("Your appointment request is awaiting acceptance by your course adviser, thank you for calling.");

          callResponse.setSay(say);
        }

        case REPORT -> {

          call.setAction(Call.Action.REPORT);

          callRepository.save(call);

          say.setValue("Please say your report after the beep, and press the # key once you are done.");

          final CallResponse.Record record = new CallResponse.Record();

          record.setFinishOnKey("#");

          record.setCallbackUrl(nextCallbackUrl("report"));

          record.setSay(say);

          callResponse.setRecord(record);
        }
      }
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
  public CallResponse result(@FormParam("dtmfDigits") final Result.Semester semester) {

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    final Call call = getCall();

    try {

      if (semester == null) throw new IllegalArgumentException();

      List<Result> results = null;

      switch (semester) {
        case FIRST -> results = resultRepository.findAllByCourseAdviserIdAndSessionIdAndSemester(
          call.getStudent().getCourseAdviser().getId(),
          call.getSession().getId(),
          Result.Semester.FIRST
        );

        case SECOND -> results = resultRepository.findAllByCourseAdviserIdAndSessionIdAndSemester(
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
  @Path("report")
  @CallActive
  @CallFetchByCallSessionId
  public CallResponse report(@FormParam("recordingUrl") final String recordingUrl) {

    final CallResponse callResponse = new CallResponse();

    final CallResponse.Say say = new CallResponse.Say();

    final Call call = getCall();

    final Report report = new Report();

    report.setRecordUrl(recordingUrl);

    report.setStudent(call.getStudent());

    reportRepository.save(report);

    say.setValue("Your report has been sent to your course adviser, thank you for calling.");

    callResponse.setSay(say);

    return callResponse;
  }

  @POST
  @Path("outbound")
  @CallActive
  @CallFetchByCallSessionId
  public CallResponse outbound() {
    final Call call = getCall();

    final CallResponse callResponse = new CallResponse();

    if (call != null) {
      final CallResponse.Say say = new CallResponse.Say();

      if (call.getAppointment() != null) {

        String message = String.format("the date of your meeting is on the %s", call.getAppointment().getStartedAt().toString());

        say.setValue(
          String.format(
            "Your course adviser has accepted your appointment request, please take this down, %s. I repeat, %s. Thanks for listening.",
            message,
            message
          )
        );

      } else if (call.getReport() != null) {

        String message = String.format("he or she said \"%s\"", call.getReport().getReply());

        say.setValue(
          String.format(
            "Your course adviser has replied to your report, please take this down, %s. I repeat, %s. Thanks for listening.",
            message,
            message
          )
        );

      } else {
        say.setValue("Sorry this is an invalid call.");
      }

      callResponse.setSay(say);
    } else {
      final CallResponse.Redirect redirect = new CallResponse.Redirect();
      redirect.setValue(redirectInvalidOutboundCall);
      callResponse.setRedirect(redirect);
    }

    return callResponse;
  }
}
