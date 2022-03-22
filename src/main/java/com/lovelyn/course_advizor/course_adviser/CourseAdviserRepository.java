package com.lovelyn.course_advizor.course_adviser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAdviserRepository extends JpaRepository<CourseAdviser, Long> {

  boolean existsByPhoneNumber(String phoneNumber);

  Optional<CourseAdviser> findByPhoneNumber(String phoneNumber);

}
