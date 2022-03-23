package com.lovelyn.course_advizor.student;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentMatriculationNumberAlreadyExistsValidator.class)
@Documented
public @interface StudentMatriculationNumberAlreadyExists {

  String message() default "Student with matriculation number already exists";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
