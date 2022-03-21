package com.lovelyn.course_advizor.course_adviser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseAdviserRepository extends JpaRepository<CourseAdviser, Long> {

  boolean existsByPhoneNumber(String phoneNumber);

}
