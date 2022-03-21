package com.lovelyn.course_advizor.validation;

import javax.validation.GroupSequence;

@GroupSequence({ValidationGroupOne.class, ValidationGroupTwo.class, ValidationGroupThree.class})
public interface ValidationGroupSequence {
}
