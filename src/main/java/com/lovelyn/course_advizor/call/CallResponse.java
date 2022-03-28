package com.lovelyn.course_advizor.call;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallResponse {

  @XmlElement(name = "GetDigits")
  private GetDigits getDigits;

  @XmlElement(name = "Record")
  private Record record;

  @XmlElement(name = "Say")
  private Say say;

  @XmlElement(name = "Redirect")
  private Redirect redirect;

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
    private Integer numDigits;

    @XmlAttribute
    private String timeout;

    @XmlAttribute
    private String finishOnKey;

    @XmlElement(name = "Say")
    private Say say;

  }

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Redirect {

    @XmlValue
    private String value;

  }

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Record {

    @XmlAttribute
    private String callbackUrl;

    @XmlAttribute
    private String finishOnKey;

    @XmlAttribute
    private String maxLength;

    @XmlAttribute
    private boolean trimSilence= true;

    @XmlAttribute
    private boolean playBeep= true ;

    @XmlElement(name = "Say")
    private Say say;
  }
}
