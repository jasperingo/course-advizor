package com.lovelyn.course_advizor.student_result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentResultRepository extends JpaRepository<StudentResult, Long> {

  boolean existsByStudentIdAndResultId(Long studentId, Long resultId);

  List<StudentResult> findAllByStudentIdAndResultId(Long studentId, Long resultId);

}
