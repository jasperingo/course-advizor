package com.lovelyn.course_advizor.course_adviser;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseAdviserIdExistsValidator.class)
@Documented
public @interface CourseAdviserIdExists {

  String message() default "Course adviser not found";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
