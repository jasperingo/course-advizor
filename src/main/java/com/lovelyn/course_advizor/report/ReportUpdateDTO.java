package com.lovelyn.course_advizor.report;

import com.lovelyn.course_advizor.validation.ValidationErrorCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReportUpdateDTO {


  @NotBlank(message = ValidationErrorCode.REPLY_REQUIRED)
  private String reply;

}
