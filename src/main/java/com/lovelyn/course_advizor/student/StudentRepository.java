package com.lovelyn.course_advizor.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

  Optional<Student> findByPhoneNumber(String phoneNumber);

  boolean existsByMatriculationNumber(String matriculationNumber);

  boolean existsByPhoneNumber(String phoneNumber);

  List<Student> findAllByCourseAdviserId(Long courseAdviserId);

  Long countByCourseAdviserId(Long courseAdviserId);

}
