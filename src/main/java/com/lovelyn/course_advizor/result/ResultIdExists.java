package com.lovelyn.course_advizor.result;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResultIdExistsValidator.class)
@Documented
public @interface ResultIdExists {

  String message() default "Result not found";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
