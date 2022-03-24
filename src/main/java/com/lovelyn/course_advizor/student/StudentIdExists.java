package com.lovelyn.course_advizor.student;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentIdExistsValidator.class)
@Documented
public @interface StudentIdExists {

  String message() default "Student not found";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
