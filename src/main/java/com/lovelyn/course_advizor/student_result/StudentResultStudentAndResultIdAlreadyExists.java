package com.lovelyn.course_advizor.student_result;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentResultStudentAndResultIdAlreadyExistsValidator.class)
@Documented
public @interface StudentResultStudentAndResultIdAlreadyExists {

  String message() default "Student result with student and result id already exists";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
