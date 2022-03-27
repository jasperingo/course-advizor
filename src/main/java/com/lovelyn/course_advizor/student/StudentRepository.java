package com.lovelyn.course_advizor.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

  Optional<Student> findByPhoneNumber(String phoneNumber);

  Optional<Student> findByMatriculationNumber(String matriculationNumber);

  boolean existsByMatriculationNumber(String matriculationNumber);

  boolean existsByPhoneNumber(String phoneNumber);

  List<Student> findAllByCourseAdviserId(Long courseAdviserId);

}
