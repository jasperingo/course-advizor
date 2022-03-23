package com.lovelyn.course_advizor.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

  boolean existsByMatriculationNumber(String matriculationNumber);

  Optional<Student> findByMatriculationNumber(String matriculationNumber);

}
