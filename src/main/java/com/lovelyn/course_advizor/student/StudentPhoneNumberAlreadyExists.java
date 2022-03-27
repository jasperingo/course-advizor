package com.lovelyn.course_advizor.student;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentPhoneNumberAlreadyExistsValidator.class)
@Documented
public @interface StudentPhoneNumberAlreadyExists {

  String message() default "Student with phone number already exists";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
