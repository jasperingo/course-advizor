package com.lovelyn.course_advizor.result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

  boolean existsByCourseAdviserIdAndSessionId(Long courseAdviserId, Long sessionId);

  boolean existsByCourseAdviserIdAndCourseCodeAndSessionIdAndSemester(Long courseAdviserId, String courseCode, Long sessionId, Result.Semester semester);

  List<Result> findAllByCourseAdviserId(Long courseAdviserId);

  List<Result> findAllByCourseAdviserIdAndSessionIdAndSemester(Long courseAdviserId, Long sessionId, Result.Semester semester);

  Long countByCourseAdviserId(Long courseAdviserId);

}
