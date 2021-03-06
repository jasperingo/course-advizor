package com.lovelyn.course_advizor.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

  @Query("SELECT a FROM Report a JOIN FETCH a.student s WHERE s.courseAdviser.id = :courseAdviserId")
  List<Report> findAllByStudentCourseAdviserId(@Param("courseAdviserId") Long courseAdviserId);

  @Query("SELECT COUNT(a) FROM Report a JOIN a.student s WHERE s.courseAdviser.id = :courseAdviserId")
  Long countByStudentCourseAdviserId(@Param("courseAdviserId") Long courseAdviserId);

  @Query("SELECT COUNT(a) FROM Report a JOIN a.student s WHERE s.courseAdviser.id = :courseAdviserId AND a.reply IS NULL")
  Long countPendingByStudentCourseAdviserId(@Param("courseAdviserId") Long courseAdviserId);

}
