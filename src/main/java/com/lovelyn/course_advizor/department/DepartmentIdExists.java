package com.lovelyn.course_advizor.department;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DepartmentIdExistsValidator.class)
@Documented
public @interface DepartmentIdExists {

  String message() default "Department not found";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
