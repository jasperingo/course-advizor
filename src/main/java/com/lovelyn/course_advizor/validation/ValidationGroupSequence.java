package com.lovelyn.course_advizor.validation;

import javax.validation.GroupSequence;

@GroupSequence({
  ValidationGroupSequence.ValidationGroupOne.class,
  ValidationGroupSequence.ValidationGroupTwo.class,
  ValidationGroupSequence.ValidationGroupThree.class,
  ValidationGroupSequence.ValidationGroupFour.class
})
public interface ValidationGroupSequence {

  interface ValidationGroupOne {}

  interface ValidationGroupTwo {}

  interface ValidationGroupThree {}

  interface ValidationGroupFour {}

}
