package com.lovelyn.course_advizor.call;

import lombok.Data;

import java.util.List;

@Data
public class CallOutboundDTO {

  private List<CallOutboundDTOEntries> entries;

  private String errorMessage;

  @Data
  public static class CallOutboundDTOEntries {

    public enum Status {
      Queued,
      InvalidPhoneNumber,
      DestinationNotSupported,
      InsufficientCredit
    }

    private String phoneNumber;

    private Status status;

    private String sessionId;

  }
}
