package com.lovelyn.course_advizor.course_adviser;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseAdviserPhoneNumberAlreadyExistsValidator.class)
@Documented
public @interface CourseAdviserPhoneNumberAlreadyExists {

  String message() default "Course Adviser with phone number not";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
