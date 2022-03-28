package com.lovelyn.course_advizor.result;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResultWithCourseCodeSessionAndSemesterAlreadyExistsValidator.class)
@Documented
public @interface ResultWithCourseCodeSessionAndSemesterAlreadyExists {

  String message() default "Result with course, session id and semester already exists";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
