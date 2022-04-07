package com.lovelyn.course_advizor.call;

import com.lovelyn.course_advizor.appointment.Appointment;
import com.lovelyn.course_advizor.report.Report;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

@Service
public class CallOutboundService {

  @Value("${af.APIKey}")
  private String africansTalkingApiKey;

  @Value("${af.APIEndPoint}")
  private String africansTalkingApiEndPoint;

  @Value("${af.appUsername}")
  private String africansTalkingAppUsername;

  @Value("${af.phoneNumber}")
  private String africansTalkingPhoneNumber;

  @Autowired
  @Setter
  private CallRepository callRepository;

  private CallOutboundDTO send(final String recipientPhoneNumber) {

    Client client = ClientBuilder.newClient();

    Form form = new Form();

    form.param("username", africansTalkingAppUsername);
    form.param("from", africansTalkingPhoneNumber);
    form.param("to", recipientPhoneNumber);

    return client.target(africansTalkingApiEndPoint)
      .request(MediaType.APPLICATION_JSON_TYPE)
      .header("apiKey", africansTalkingApiKey)
      .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), CallOutboundDTO.class);
  }

  @Async
  public void sendCall(final Appointment appointment) {

    final CallOutboundDTO callOutboundDTO = send(appointment.getStudent().getPhoneNumber());

    callOutboundDTO.getEntries().forEach(callOutboundDTOEntry -> {
      Call call = new Call();

      call.setStudent(appointment.getStudent());

      call.setStatus(Call.Status.ACTIVE);

      call.setCallSessionId(callOutboundDTOEntry.getSessionId());

      call.setCallDirection(Call.CallDirection.Outbound);

      call.setAppointment(appointment);

      callRepository.save(call);
    });
  }

  @Async
  public void sendCall(final Report report) {

    final CallOutboundDTO callOutboundDTO = send(report.getStudent().getPhoneNumber());

    callOutboundDTO.getEntries().forEach(callOutboundDTOEntry -> {
      Call call = new Call();

      call.setStudent(report.getStudent());

      call.setStatus(Call.Status.ACTIVE);

      call.setCallSessionId(callOutboundDTOEntry.getSessionId());

      call.setCallDirection(Call.CallDirection.Outbound);

      call.setReport(report);

      callRepository.save(call);
    });
  }
}
