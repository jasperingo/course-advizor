package com.lovelyn.course_advizor.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

  boolean existsByMatriculationNumber(String matriculationNumber);

  List<Student> findAllByCourseAdviserId(Long courseAdviserId);

}
