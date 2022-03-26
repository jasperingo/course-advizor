package com.lovelyn.course_advizor.result;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentResultStudentAndResultIdNotChangedValidator.class)
@Documented
public @interface StudentResultStudentAndResultIdNotChanged {

  String message() default "Student Result student and result id cannot be changed";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
