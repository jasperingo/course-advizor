package com.lovelyn.course_advizor.course_adviser;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseAdviserWithSessionIdAndDepartmentIdAlreadyExistsValidator.class)
@Documented
public @interface CourseAdviserWithSessionIdAndDepartmentIdAlreadyExists {

  String message() default "Course Adviser with session id and department id already exists";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
