package com.lovelyn.course_advizor.session;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SessionIdExistsValidator.class)
@Documented
public @interface SessionIdExists {

  String message() default "Section not found";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
