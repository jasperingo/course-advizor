package com.lovelyn.course_advizor.call;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallResponse {

  @XmlElement(name = "Say")
  private Say say;

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Say {

    @XmlValue
    private String value;

  }

}
