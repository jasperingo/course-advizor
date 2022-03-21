package com.lovelyn.course_advizor.section;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SectionIdExistsValidator.class)
@Documented
public @interface SectionIdExists {

  String message() default "Section not found";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
