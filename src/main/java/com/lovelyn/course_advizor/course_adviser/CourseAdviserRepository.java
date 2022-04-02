package com.lovelyn.course_advizor.course_adviser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAdviserRepository extends JpaRepository<CourseAdviser, Long> {

  boolean existsByPhoneNumber(String phoneNumber);

  boolean existsBySessionIdAndDepartmentId(Long sessionId, Long departmentId);

  Optional<CourseAdviser> findByPhoneNumber(String phoneNumber);

}
