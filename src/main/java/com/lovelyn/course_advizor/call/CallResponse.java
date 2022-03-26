package com.lovelyn.course_advizor.call;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallResponse {

  @XmlElement(name = "Say")
  private Say say;

  @XmlElement(name = "GetDigits")
  private GetDigits getDigits;

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Say {

    @XmlValue
    private String value;

    @XmlAttribute
    private boolean playBeep;

  }

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class GetDigits {

    @XmlAttribute
    private String callbackUrl;

    @XmlAttribute
    private String numDigits;

    @XmlAttribute
    private String timeout;

    @XmlAttribute
    private String finishOnKey;

    @XmlElement(name = "Say")
    private Say say;

  }

}
